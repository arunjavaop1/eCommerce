package com.ecommerce.service.user_service;

import com.ecommerce.dto.user_dto.UserDto;
import com.ecommerce.entity.Address;
import com.ecommerce.entity.Role;
import com.ecommerce.entity.User;
import com.ecommerce.exception.UserFoundException;
import com.ecommerce.mapper.UserMapper;

import com.ecommerce.payload.EmailDetails;
import com.ecommerce.repository.AddressRepo;
import com.ecommerce.repository.UserRepo;

import com.ecommerce.service.email_service.EmailServiceImpl;
import com.ecommerce.service.role_service.RoleServiceImpl;
import com.ecommerce.service.security_service.CustomUserDetailsService;
import com.ecommerce.utility.UserStatus;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder encoder;
    private final RoleServiceImpl roleService;
    private final EmailServiceImpl emailService;

    private final AddressRepo addressRepo;
    private final CustomUserDetailsService cs;


    public UserServiceImpl(UserRepo userRepo, PasswordEncoder encoder, RoleServiceImpl roleService, EmailServiceImpl emailService, AddressRepo addressRepo, CustomUserDetailsService cs) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.roleService = roleService;
        this.emailService = emailService;
        this.addressRepo = addressRepo;
        this.cs = cs;
    }

    @Override
    @Transactional
    public UserDto registerUser(User user, long roleId) {

        if(userRepo.findByEmail(user.getEmail()).isPresent()) throw new UserFoundException("User already found");
        user.setPassword(encoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setEmailVerified(false);
        user.setStatus(UserStatus.ACTIVE);

        Role role = roleService.findRoleExistOrNot(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        if(role == null) System.out.println("role not found");
        user.setRoles(Set.of(role));

        User savedUser = userRepo.save(user);

        return UserMapper.userToDto(savedUser);

    }


    @Override
    @Transactional
    public UserDto updateUserProfile(User user) {
        User existingUser = userRepo.findById(user.getId()).orElse(null);
        if (existingUser == null) return null;

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setContact("8709288463");
        existingUser.setUpdatedAt(LocalDateTime.now());

        User updatedUser = userRepo.save(existingUser);
        return UserMapper.userToDto(updatedUser);
    }


    @Transactional
    public boolean changePassword(String email, String newPassword) {
        User existingUser = userRepo.findByEmail(email).orElse(null);
        if(existingUser == null) return false;
        existingUser.setPassword(encoder.encode(newPassword));
        return true;
    }


    public UserDto getUserById(Long id){
        User existingUser = userRepo.findById(id).orElse(null);
        if(existingUser == null) return null;
        return UserMapper.userToDto(existingUser);
    }


    public UserDto getUserByEmail(String email){
        User existingUser = userRepo.findByEmail(email).orElse(null);
        if(existingUser == null) return null;
        return UserMapper.userToDto(existingUser);
    }

    @Transactional
    public boolean deactivateUser(Long id) {
        User existingUser = userRepo.findById(id).orElse(null);

        if(existingUser == null) return false;
        existingUser.setStatus(UserStatus.INACTIVE);
        return true;
    }

    @Override
    public boolean activateUser(Long id) {
        return false;
    }

    @Override
    public boolean verifyEmail(String mail) {
        User user = userRepo.findByEmail(mail).orElse(null);
        if(user == null) return false;
        EmailDetails details = new EmailDetails();

        details.setSubject("\"Email Verification Successful - ApnaJob\"");
        details.setRecipient(user.getEmail());
        details.setMsgBody("Hello ,\n\n" + user.getUsername() +
                "We are excited to inform you that your email address has been successfully verified on the ApnaJob application.\n\n" +
                "You can now enjoy full access to our platform and continue building your career journey with us.\n\n" +
                "If you did not initiate this verification, please contact our support team immediately.\n\n" +
                "Best regards,\n" +
                "ApnaJob Team"
        );

        if(emailService.sendSimpleMail(details) ) {
            user.setEmailVerified(true);
            userRepo.save(user);
            return true;
        }

        return true;
    }


    @Override
    @Transactional
    public List<UserDto> findAllUser() {

        List<User> userList = userRepo.findAll();

        return userList.stream()
                .map(UserMapper::userToDto)
                .toList();
    }

    @Override
    public UserDto deleteUser(String email) {
        Optional<User> user = userRepo.findByEmail(email);
        user.ifPresent(u -> u.setStatus(UserStatus.DELETE));
        if(user.isEmpty()) return null;
        userRepo.save(user.get());
        return UserMapper.userToDto(user.get());
    }

    @Override
    public UserDto updatePhoneDetails(String email, String contact) {

        Optional<User> existingUser = userRepo.findByEmail(email);
        if(existingUser.isEmpty()) return null;

        User user = existingUser.get();

        user.setContact(contact);
        user.setUpdatedAt(LocalDateTime.now());

        userRepo.save(user);
        return UserMapper.userToDto(user);
    }


    @Override
    @Transactional
    public UserDto updateEmail(String oldEmail, String newEmail) {

        if(userRepo.findByEmail(newEmail).isPresent()) {
            throw new IllegalArgumentException("Email already in use.");
        }

        Optional<User> existingUser = userRepo.findByEmail(oldEmail);
        User user = existingUser.orElse(null);
        if(user == null) return null;

        user.setEmail(newEmail);

        userRepo.save(user);

        return UserMapper.userToDto(user);
    }

    @Override
    public UserDto updateProfilePicture(String email, String imageUrl) {
        return null;
    }

    @Override
    public UserDto updateRoles(String email, Set<Role> roles) {
        return null;
    }

    @Override
    public UserDto addAddress(String email, Address address) {
        return null;
    }

    @Override
    public UserDto removeAddress(String email, Long addressId) {
        User user = userRepo.findByEmail(email).orElse(null);

        if(user == null) return null;


        List<Address> addressList = user.getAddresses();
        for(Address address : addressList) {
            if(address.getId() == addressId) {
                addressRepo.deleteById(addressId);
                break;
            }
        }

        return UserMapper.userToDto(user);
    }

    @Override
    public UserDto getUserByContact(String contact) {
        return null;
    }
}
