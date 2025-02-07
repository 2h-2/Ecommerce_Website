package com.ecomerce.backend.model.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;



@Entity
@Table(name = "order_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="item_id")
    private long itemId;

    private String photo;   

    @Column(nullable = false)    
    private String name;   

    @Column(nullable = false)    
    private String description; 

    @Column(length = 5, nullable = false)
    private String size;

    @Column(length = 20, nullable = false)
    private String color;

    @Column(nullable = false) 
    private BigDecimal price;  

    @Column(nullable = false)  
    private int quantity;      

    private BigDecimal subtotal;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false, referencedColumnName = "order_id")
    @JsonBackReference
    private Order order;
}
