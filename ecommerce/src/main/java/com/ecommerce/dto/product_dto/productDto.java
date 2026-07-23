package com.ecommerce.dto.product_dto;

import com.ecommerce.dto.user_dto.UserDto;
import com.ecommerce.entity.User;
import com.ecommerce.utility.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public record ProductDto(
    String sku,
    String productName,
    String description,
    double price,
    Integer stockQuantity,
    ProductStatus status,
    UserDto seller
) {}
