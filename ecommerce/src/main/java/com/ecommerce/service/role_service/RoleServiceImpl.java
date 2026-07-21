package com.ecommerce.service.role_service;

import com.ecommerce.entity.Role;
import com.ecommerce.repository.RoleRepo;
import com.ecommerce.utility.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public Optional<Role> findRoleExistOrNot(long id) {
        return roleRepo.findById(id);
    }
}
