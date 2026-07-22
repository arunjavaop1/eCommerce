package com.ecommerce.service.user_service;

import com.ecommerce.dto.user_dto.UserDto;
import com.ecommerce.entity.Address;
import com.ecommerce.entity.Role;
import com.ecommerce.entity.User;

import java.util.List;

public interface UserService {

    // Core user lifecycle
    UserDto registerUser(User user, long id);
    UserDto updateUserProfile(User user);
    boolean changePassword(String email, String newPassword);
    UserDto getUserById(Long id);
    UserDto getUserByEmail(String email);
    boolean deactivateUser(String email);
    boolean activateUser(String email);

    boolean verifyEmail(String email);
    List<UserDto> findAllUser();
    UserDto deleteUser(String email);

    // Contact & profile updates
    UserDto updateEmail(String oldEmail, String newEmail);
    UserDto updateProfilePicture(String email, String imageUrl);
    UserDto updatePhoneNumber(String email, String phone_number);

    // Role management
    UserDto updateRoles(String email, Role role);

    // Address management
    UserDto addAddress(String email, Address address);
    UserDto removeAddress(String email, Long addressId);

}
