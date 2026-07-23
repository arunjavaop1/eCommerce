package com.ecommerce.service.category_service;

import com.ecommerce.entity.Category;
import com.ecommerce.repository.CategoryRepo;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepo categoryRepo;

    public CategoryServiceImpl(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public Category findCategoryById(long id) {
        return categoryRepo.findById(id).orElse(null);
    }
}
