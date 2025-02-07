package com.ecomerce.backend.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.ecomerce.backend.model.dto.CheckoutDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "`order`")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "buyer_email" , nullable = false)
    private String buyerEmail;

    @Column(name = "buyer_name" , nullable = false)
    private String buyerName;


    @Column(name = "buyer_phone", nullable = false)
    private String buyerPhone;

    @Column(name = "buyeraddress", nullable = false)
    private String buyerAddress;

    @Column(nullable = false)
    private String city;

    // Total Amount
    @Column(name = "total", precision = 10, scale = 2)
    private BigDecimal orderAmount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderItem> items;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference 
    private User user;

    @Column(name = "status", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer orderStatus;

    public Order(CheckoutDto dto) {

        this.buyerName = dto.getName();
        this.buyerEmail = dto.getEmail();
        this.buyerPhone = dto.getPhone();
        this.buyerAddress = dto.getAddress();
        this.city = dto.getCity();
        this.orderAmount = dto.getTotal();
        this.orderStatus = 0;
    }

}
