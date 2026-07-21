package com.ecommerce.dto.user_dto;

import com.ecommerce.entity.Address;
import com.ecommerce.entity.Role;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private boolean email_verified;
    private List<Address> address;
    private Set<Role> role;
}
