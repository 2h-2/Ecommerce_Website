package com.ecomerce.backend.model.dto;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import com.ecomerce.backend.model.entity.Color;
import com.ecomerce.backend.model.entity.Product;
import com.ecomerce.backend.model.entity.Size;
import com.ecomerce.backend.model.entity.Category;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
public class ProductDto {
    @Data
    public class SizeDto {
        private Long id;
        private String name;
    
        public SizeDto(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }
    @Data
    public class ColorDto {
        private Long id;
        private String name;
        }
    private Long id;
    @NotBlank(message = "Name is required.")
    public String name;
    
    @NotBlank(message = "description is requered.")
    private String description;
    
    private BigDecimal unitPrice;
    private String material;
    private String style;
    private Set<Color> colors;
    private Set<Size> sizes;
    private Category category;
    private Double discount;

    

    public static ProductDto toDto(Product product) {

    Set<Size> sizeDtos = product.getSizes().stream()
        .map(size -> new Size(size.getId(), size.getName()))
        .collect(Collectors.toSet());

    Set<Color> colorDtos = product.getColors().stream()
            .map(color -> new Color(color.getId(), color.getName()))
            .collect(Collectors.toSet());
    
    ProductDto dto = new ProductDto();
    dto.setId(product.getId());
    dto.setName(product.getName());
    dto.setDescription(product.getDescription());
    dto.setUnitPrice(product.getUnitPrice());
    dto.setDiscount(product.getDiscount());
    dto.setSizes(sizeDtos);
    dto.setColors(colorDtos);

    return dto;
}
}

