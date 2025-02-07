package com.ecomerce.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecomerce.backend.model.entity.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long>{
    List<OrderItem> findByOrderId(Long id);
}
