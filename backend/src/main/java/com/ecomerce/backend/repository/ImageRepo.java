package com.ecomerce.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecomerce.backend.model.entity.ProductImages;
import org.springframework.stereotype.Repository;
@Repository

public interface ImageRepo extends JpaRepository<ProductImages, Long> {
    
}
