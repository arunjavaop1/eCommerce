package com.ecommerce.controller.address_controller;

import com.ecommerce.dto.user_dto.UserDto;
import com.ecommerce.entity.Address;
import com.ecommerce.service.address_service.AddressServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressServiceImpl addressService;

    public AddressController(AddressServiceImpl addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/save-address")
    public ResponseEntity<?> saveAddress(@RequestBody Address address) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        System.out.println(username);

        UserDto userDto = null;
        try {
            userDto = addressService.saveAddress(username, address);
        } catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Address not saved");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @DeleteMapping("/delete-address/{id}")
    public ResponseEntity<?> deleteAddressById(@PathVariable  long id) {
        boolean success = addressService.deleteById(id);

        if(success) {
            return ResponseEntity.status(HttpStatus.OK).body("Dleete from db");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Address not deleted");
    }
}
