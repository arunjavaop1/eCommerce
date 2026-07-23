package com.ecommerce.controller.seller_controller;

import com.ecommerce.dto.user_dto.UserDto;
import com.ecommerce.entity.User;
import com.ecommerce.exception.UserFoundException;
import com.ecommerce.service.user_service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
public class SellerController {

    private final UserServiceImpl userService;

    public SellerController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        try {
            UserDto savedUser = userService.registerUser(user, 3L);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (UserFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already present");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Role not found");
        }
    }
}
