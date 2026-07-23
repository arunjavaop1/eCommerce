package com.ecommerce.controller.product_controller;

import com.ecommerce.dto.product_dto.ProductDto;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.service.product_service.ProductServiceImpl;
import com.ecommerce.service.security_service.CustomUserDetailsService;
import com.ecommerce.service.user_service.UserServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/products")
public class ProductController {

    private final UserServiceImpl userService;
    private final ProductServiceImpl productService;
    private final CustomUserDetailsService cd;

    public ProductController(UserServiceImpl userService, ProductServiceImpl productService, CustomUserDetailsService cd) {
        this.userService = userService;
        this.productService = productService;
        this.cd = cd;
    }

    @PostMapping("/save-product")
    public ResponseEntity<?> registerProduct(@RequestBody Product product) {
        try {
            String email = cd.getAuthentication();
            ProductDto savedProduct = productService.registerProduct(email, product);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        } catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product not saved in database" + e.getMessage());
        }
    }

    @GetMapping("/all-products")
    public ResponseEntity<?> getAllProduct(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        try {
            Page<ProductDto> pages = productService.findAll(pageable);
            return ResponseEntity.status(HttpStatus.OK).body(pages);
        } catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Products not found \n" + e.getMessage());
        }
    }


    @GetMapping("/buy-product/{product_id}")
    public ResponseEntity<?> buyProduct(@PathVariable("product_id") long product_id) {
        User existing_user = userService.getExistingUser(cd.getAuthentication());
        ProductDto productHasBuy = productService.buyProduct(existing_user, product_id);

    }


}
