package com.ecomerce.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecomerce.backend.model.entity.Order;

public interface OrderRepo extends JpaRepository<Order, Long>{
    List<Order> findByUserId(Long id);
}
