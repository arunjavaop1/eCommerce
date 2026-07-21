package com.ecommerce.service.product_service;

import com.ecommerce.entity.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product registerProduct(Product product);
}
