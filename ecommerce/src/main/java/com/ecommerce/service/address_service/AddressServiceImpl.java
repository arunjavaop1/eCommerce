package com.ecommerce.service.address_service;

import com.ecommerce.dto.user_dto.UserDto;
import com.ecommerce.entity.Address;
import com.ecommerce.entity.User;
import com.ecommerce.mapper.UserMapper;
import com.ecommerce.repository.AddressRepo;
import com.ecommerce.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService{

    private final AddressRepo addressRepo;
    private final UserRepo userRepo;

    public AddressServiceImpl(AddressRepo addressRepo, UserRepo userRepo) {
        this.addressRepo = addressRepo;
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public UserDto saveAddress(String username, Address address) {
        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        address.setUser(user);
        address.setCreatedAt(LocalDateTime.now());
        address.setUpdatedAt(LocalDateTime.now());


        user.getAddresses().add(address);

        userRepo.save(user);

        return UserMapper.userToDto(user);
    }


    @Override
    public boolean deleteById(long id) {
        try {
            addressRepo.deleteById(id);
            return true;
        } catch(RuntimeException e) {
            return false;
        }
    }


}
