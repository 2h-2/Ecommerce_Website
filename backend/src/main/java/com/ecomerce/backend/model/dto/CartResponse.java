package com.ecomerce.backend.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ecomerce.backend.model.entity.Cart;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartResponse {
    private Long cartId;
    private String productName;
        private Integer quantity;
        private BigDecimal price;
        private String color;
        private String size;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static CartResponse toCartResponse(Cart cart) {
        return CartResponse.builder()
                .cartId(cart.getCartId())
                .quantity(cart.getQuantity())
                .createdAt(cart.getCreatedAt())
                .updatedAt(cart.getUpdatedAt())
                .build();
    }
}
