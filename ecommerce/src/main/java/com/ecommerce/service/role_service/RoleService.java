package com.ecommerce.service.role_service;

import com.ecommerce.entity.Role;
import com.ecommerce.utility.RoleType;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findRoleExistOrNot(long id);
}
