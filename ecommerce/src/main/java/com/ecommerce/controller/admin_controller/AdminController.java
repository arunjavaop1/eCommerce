package com.ecommerce.controller.admin_controller;

import com.ecommerce.dto.user_dto.UserDto;
import com.ecommerce.entity.User;
import com.ecommerce.service.user_service.UserServiceImpl;
import com.ecommerce.utility.RoleType;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userService;

    public AdminController(UserServiceImpl userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@RequestBody User user) {
        UserDto savedAdmin = userService.registerUser(user, 2);

        if(savedAdmin != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAdmin);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Admin Not saved");
    }



}
