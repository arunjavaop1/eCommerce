package com.ecommerce.service.product_service;

import com.ecommerce.dto.product_dto.ProductDto;
import com.ecommerce.entity.Product;

import com.ecommerce.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductDto registerProduct(String email, Product product);
    Page<ProductDto> findAll(Pageable pageable) throws RuntimeException;

    ProductDto buyProduct(User user, long product_id);
}
