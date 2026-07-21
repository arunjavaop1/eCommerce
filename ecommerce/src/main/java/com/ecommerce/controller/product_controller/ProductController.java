package com.ecommerce.controller.product_controller;

import com.ecommerce.entity.Product;
import com.ecommerce.service.product_service.ProductServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @PostMapping("saveProduct")
    public ResponseEntity<?> registerProduct(@RequestBody Product product) {
        Product saveProduct = productService.registerProduct(product);

        if(saveProduct != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(product);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product not saved in database");
    }
}
