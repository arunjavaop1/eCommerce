package com.ecommerce.service.address_service;

import com.ecommerce.dto.user_dto.UserDto;
import com.ecommerce.entity.Address;

public interface AddressService {
    UserDto saveAddress(String username, Address address);

    boolean deleteById(long id);
}
