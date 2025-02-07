package com.ecomerce.backend.model.dto;


import lombok.*;

@Builder
@Data
@AllArgsConstructor
public class CartDto {
    private Long user_id;
    private Long product_id;
    private Long size_id;
    private Long color_id;
    private int quantity;
}
