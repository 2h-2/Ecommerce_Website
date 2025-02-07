package com.ecomerce.backend.model.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDto {
    private String name;
    private String description;
}
