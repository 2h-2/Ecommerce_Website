package com.ecomerce.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecomerce.backend.model.entity.Color;

public interface ColorRepo extends JpaRepository<Color, Long>  {
    
}
