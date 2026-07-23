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
import com.ecommerce.utility.UserStatus;
import jakarta.transaction.Transactional;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder encoder;
    private final RoleServiceImpl roleService;
    private final EmailServiceImpl emailService;
    private final AddressRepo addressRepo;

    public UserServiceImpl(UserRepo userRepo, PasswordEncoder encoder, RoleServiceImpl roleService, EmailServiceImpl emailService, AddressRepo addressRepo) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.roleService = roleService;
        this.emailService = emailService;
        this.addressRepo = addressRepo;
    }

    @Transactional
    @Override
    public UserDto registerUser(User user, long roleId) {

        if(userRepo.findByEmail(user.getEmail()).isPresent()) throw new UserFoundException("User already found");
        user.setPassword(encoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setEmailVerified(false);
        user.setStatus(UserStatus.ACTIVE);

        Role role = roleService.findRoleExistOrNot(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        if(role == null) return null;

        user.setRoles(Set.of(role));

        User savedUser = userRepo.save(user);

        return UserMapper.userToDto(savedUser);

    }


    @Transactional
    @Override
    public UserDto updateUserProfile(User user) {
        User existingUser = userRepo.findById(user.getId()).orElse(null);
        if (existingUser == null) return null;

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setContact(user.getContact());
        existingUser.setUpdatedAt(LocalDateTime.now());
        User updatedUser = userRepo.save(existingUser);
        return UserMapper.userToDto(updatedUser);
    }

    @Transactional
    public boolean changePassword(String email, String newPassword) {
        User existingUser = getExistingUser(email);
        if(existingUser == null) return false;
        existingUser.setPassword(encoder.encode(newPassword));
        userRepo.save(existingUser);
        return true;
    }

    @Override
    public UserDto getUserById(Long id){
        User existingUser = userRepo.findById(id).orElse(null);
        if(existingUser == null) return null;
        return UserMapper.userToDto(existingUser);
    }


    @Override
    public UserDto getUserByEmail(String email){
        User existingUser = getExistingUser(email);
        if(existingUser == null) return null;
        return UserMapper.userToDto(existingUser);
    }


    @Transactional
    @Override
    public boolean deactivateUser(String email) {
        User existingUser = getExistingUser(email);

        if(existingUser == null) return false;
        existingUser.setStatus(UserStatus.INACTIVE);
        existingUser.setUpdatedAt(LocalDateTime.now());
        userRepo.save(existingUser);
        return true;
    }

    @Transactional
    @Override
    public boolean activateUser(String email) {
        User existing_user = getExistingUser(email);

        if(existing_user == null) throw new UserFoundException("user not found");

        existing_user.setStatus(UserStatus.ACTIVE);
        existing_user.setUpdatedAt(LocalDateTime.now());
        userRepo.save(existing_user);

        return true;
    }

    @Override
    public boolean verifyEmail(String mail) {
        User user = getExistingUser(mail);
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

        try {
            boolean sent = emailService.sendSimpleMail(details);
            if(sent) {
                user.setEmailVerified(true);
                userRepo.save(user);
                return true;
            }
            return false;
        } catch(MailException m) {
            return false;
        }
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
        User user = getExistingUser(email);
        user.setStatus(UserStatus.DELETE);
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

        User user = getExistingUser(oldEmail);
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
    public UserDto updatePhoneNumber(String email, String phone_number) {
        User existing_user = getExistingUser(email);

        if(existing_user == null) {
            return null;
        }
        existing_user.setContact(phone_number);
        existing_user.setUpdatedAt(LocalDateTime.now());
        userRepo.save(existing_user);
        return UserMapper.userToDto(existing_user);
    }

    @Override
    public UserDto updateRoles(String email, Role role) {
        User existing_user = getExistingUser(email);

        if(existing_user == null) {
            return null;
        }
        Set<Role> roles = new HashSet<>(existing_user.getRoles());
        roles.add(role);
        existing_user.setRoles(roles);
        userRepo.save(existing_user);
        return UserMapper.userToDto(existing_user);
    }

    @Override
    public UserDto addAddress(String email, Address address) {
        User user = getExistingUser(email);
        if(user == null) return null;
        address.setUser(user);
        addressRepo.save(address);
        user.getAddresses().add(address);
        userRepo.save(user);
        return UserMapper.userToDto(user);
    }



    @Override
    public UserDto removeAddress(String email, Long addressId) {
        User user = getExistingUser(email);
        if(user == null) return null;

        user.getAddresses().removeIf(a -> a.getId() == addressId);
        addressRepo.deleteById(addressId);
        userRepo.save(user);

        return UserMapper.userToDto(user);
    }


    ///  Helping methods;
    public User getExistingUser(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

}
