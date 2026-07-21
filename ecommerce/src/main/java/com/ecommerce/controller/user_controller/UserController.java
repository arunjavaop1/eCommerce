package com.ecommerce.controller.user_controller;

import com.ecommerce.dto.user_dto.UserDto;

import com.ecommerce.entity.User;
import com.ecommerce.exception.UserFoundException;
import com.ecommerce.service.security_service.CustomUserDetailsService;
import com.ecommerce.service.user_service.UserServiceImpl;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;
    private final CustomUserDetailsService cd;


    public UserController(UserServiceImpl userService, CustomUserDetailsService cd) {
        this.userService = userService;
        this.cd = cd;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        try {
            UserDto savedUser = userService.registerUser(user, 1L);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (UserFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already present");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Role not found");
        }
    }

    @GetMapping("/verify_mail")
    public ResponseEntity<?> verifyEmail() {
        String email = cd.getAuthentication();
        boolean verified = userService.verifyEmail(email);
        if(verified) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Email Verified");
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Email not verified");
    }


    @GetMapping("/getAllUser")
    public ResponseEntity<?> getAllUser() {
        List<UserDto> users= userService.findAllUser();
        if (users.isEmpty()) {
            return ResponseEntity.ok("No user found");
        }
        return ResponseEntity.ok(users);
    }


    @PatchMapping("/update-phone/{contact}")
    public ResponseEntity<?> updatePhoneNumber(@PathVariable String contact) {
        String email = cd.getAuthentication();
        UserDto userDto = userService.updatePhoneDetails(email, contact);

        if(userDto == null) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not updated");

        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser() {

        String email = cd.getAuthentication();

        UserDto userDto = userService.deleteUser(email);

        if(userDto == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to delete");
        }

        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @PatchMapping("/change-password/{newPassword}")
    public ResponseEntity<?> changePassword(@PathVariable String newPassword) {
        String email = cd.getAuthentication();
        boolean success = userService.changePassword(email, newPassword);

        if(success) return ResponseEntity.status(HttpStatus.OK).body("Password changed successfully.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sorry, Your password is not changed.");
    }

    @PatchMapping("/change-email/{newEmail}")
    public ResponseEntity<?> changeOldMailToEmail(@PathVariable String newEmail) {
        String oldEmail = cd.getAuthentication();

        try {
            UserDto userDto = userService.updateEmail(oldEmail, newEmail);
            if(userDto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unable to change");
            }
            return ResponseEntity.status(HttpStatus.OK).body(userDto);
        } catch(IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("new Email already in use.");
        }

    }
}
