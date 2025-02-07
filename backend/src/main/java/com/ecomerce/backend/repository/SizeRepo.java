package com.ecomerce.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecomerce.backend.model.entity.Size;

public interface SizeRepo extends JpaRepository<Size, Long> {
    
}
