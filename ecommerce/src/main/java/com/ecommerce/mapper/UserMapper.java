package com.ecommerce.mapper;

import com.ecommerce.dto.user_dto.UserDto;
import com.ecommerce.entity.User;

public class UserMapper {

    public static UserDto userToDto(User user) {
        UserDto user_info = new UserDto();
        user_info.setId(user.getId());
        user_info.setUsername(user.getUsername());
        user_info.setEmail(user.getEmail());
        user_info.setAddress(user.getAddresses());
        user_info.setRole(user.getRoles());
        user_info.setEmail_verified(user.getEmailVerified());
        return user_info;
    }
}
