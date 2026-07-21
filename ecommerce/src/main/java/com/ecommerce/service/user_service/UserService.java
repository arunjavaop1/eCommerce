package com.ecommerce.service.user_service;

import com.ecommerce.dto.user_dto.UserDto;
import com.ecommerce.entity.Address;
import com.ecommerce.entity.Role;
import com.ecommerce.entity.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    // Core user lifecycle
    UserDto registerUser(User user, long id);
    UserDto updateUserProfile(User user);
    boolean changePassword(String email, String newPassword);
    UserDto getUserById(Long id);
    UserDto getUserByEmail(String email);
    boolean deactivateUser(Long id);
    boolean activateUser(Long id);
    boolean verifyEmail(String email);
    List<UserDto> findAllUser();
    UserDto deleteUser(String email);

    // Contact & profile updates
    UserDto updatePhoneDetails(String email, String contact);
    UserDto updateEmail(String oldEmail, String newEmail);
    UserDto updateProfilePicture(String email, String imageUrl);

    // Role management
    UserDto updateRoles(String email, Set<Role> roles);

    // Address management
    UserDto addAddress(String email, Address address);
    UserDto removeAddress(String email, Long addressId);

    // Search helpers
    UserDto getUserByContact(String contact);
}
