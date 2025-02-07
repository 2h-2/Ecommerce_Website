package com.ecomerce.backend.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import com.ecomerce.backend.model.dto.ApiResponse;
import com.ecomerce.backend.model.entity.*;
import com.ecomerce.backend.repository.OrderRepo;
import com.ecomerce.backend.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    
    @Autowired
    OrderService orderService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<Order>>> getCartItemCountForUser(@PathVariable Long userId) {
        
        List<Order> data = orderService.getOrders(userId);

        if(data == null){
            ApiResponse<List<Order>> response = ApiResponse.of(
                "",
                "No Order yet!",
                null
        );
        return ResponseEntity.ok(response);
        }

        ApiResponse<List<Order>> response = ApiResponse.of(
                "success",
                "Data retrieved successfully",
                data
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<Order>>> getAll() {
        
        List<Order> data = orderService.getAllOrders();

        if(data == null){
            ApiResponse<List<Order>> response = ApiResponse.of(
                "",
                "No Order yet!",
                null
        );
        return ResponseEntity.ok(response);
        }

        ApiResponse<List<Order>> response = ApiResponse.of(
                "success",
                "Data retrieved successfully",
                data
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Order> get(@PathVariable("id") Long orderId) {
        return ResponseEntity.ok(orderService.get(orderId));
    }
    
    @PatchMapping("/cancel/{id}")
    public ResponseEntity<Order> cancel(@PathVariable("id") Long orderId) {
        return ResponseEntity.ok(orderService.cancel(orderId));
    }

    @PatchMapping("/finish/{id}")
    public ResponseEntity<Order> finish(@PathVariable("id") Long id) {
        return ResponseEntity.ok(orderService.finish(id));
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<List<OrderItem>> getItems(@PathVariable("id") Long id) {
        return ResponseEntity.ok(orderService.getAllItems(id));
    }
}

