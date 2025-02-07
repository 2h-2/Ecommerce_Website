package com.ecomerce.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecomerce.backend.model.dto.CategoryDto;
import com.ecomerce.backend.model.entity.Category;
import com.ecomerce.backend.repository.CategoryRepo;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;

    public void add(CategoryDto category){
        Category cat = new Category();
        cat.setCategoryName(category.getName());
        categoryRepo.save(cat);
    }

    public List<Category> getAll(){
        return categoryRepo.findAll();
    }
}
