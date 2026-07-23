package com.ecommerce.mapper;

import com.ecommerce.dto.product_dto.ProductDto;
import com.ecommerce.entity.Product;

public class ProductMapper {
    public static ProductDto productToDto(Product product) {
        return new ProductDto(
            product.getSku(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getStockQuantity(),
            product.getStatus(),
            UserMapper.userToDto(product.getSeller())
        );
    }
}
