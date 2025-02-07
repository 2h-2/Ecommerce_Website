
package com.ecomerce.backend.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.ecomerce.backend.model.dto.CartDto;


@Entity
@Table(name = "cart")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "color_id", nullable = false)
    private Long colorId;

    @Column(name = "size_id", nullable = false)
    private Long sizeId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

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

   public static Cart toCart(CartDto cartDto){
        return Cart.builder()
        .userId(cartDto.getUser_id())
        .productId(cartDto.getProduct_id())
        .quantity(cartDto.getQuantity())
        .sizeId(cartDto.getSize_id())
        .colorId(cartDto.getColor_id())
        .build();
    }

}
