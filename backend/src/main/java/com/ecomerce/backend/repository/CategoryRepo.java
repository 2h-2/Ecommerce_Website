package com.ecomerce.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecomerce.backend.model.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    
}
