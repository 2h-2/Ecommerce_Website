package com.ecomerce.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecomerce.backend.model.entity.Product;

import org.springframework.stereotype.Repository;
@Repository

public interface ProductRepo extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p where p.id = :id")
    Product findAllWithColorsAndSizes(@Param("id") int id);
}