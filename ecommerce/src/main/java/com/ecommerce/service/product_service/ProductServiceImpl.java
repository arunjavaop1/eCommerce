package com.ecommerce.service.product_service;

import com.ecommerce.dto.product_dto.ProductDto;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.mapper.ProductMapper;
import com.ecommerce.repository.ProductRepo;
import com.ecommerce.repository.UserRepo;
import com.ecommerce.service.category_service.CategoryServiceImpl;
import com.ecommerce.service.user_service.UserServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepo productRepo;
    private final CategoryServiceImpl categoryService;
    private final UserServiceImpl userService;

    public ProductServiceImpl(ProductRepo productRepo, CategoryServiceImpl categoryService, UserServiceImpl userService) {
        this.productRepo = productRepo;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @Transactional
    @Override
    public ProductDto registerProduct(String email, Product product) {
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        long categoryId = product.getCategory().getId();
        Category category = categoryService.findCategoryById(categoryId);
        User seller = userService.getExistingUser(email);
        if (category == null) {
            throw new RuntimeException("Category not found with id: " + categoryId);
        }
        product.setCategory(category);
        product.setSeller(seller);
        Product savedProduct = productRepo.save(product);
        return ProductMapper.productToDto(savedProduct);
    }

    @Override
    public Page<ProductDto> findAll(Pageable pageable) throws RuntimeException{
        int startRow = pageable.getPageNumber() * pageable.getPageSize();
        int endRow = startRow + pageable.getPageSize();
        List<Product> products = productRepo.findProductsWithPagination(startRow, endRow);
        List<ProductDto> dtos = products.stream()
                .map(ProductMapper::productToDto)
                .toList();
        return new PageImpl<>(dtos, pageable, dtos.size());

    }

    @Override
    public ProductDto buyProduct(User user, long productId) {
        Product
    }


}
