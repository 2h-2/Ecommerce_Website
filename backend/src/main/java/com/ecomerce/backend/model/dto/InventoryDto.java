package com.ecomerce.backend.model.dto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryDto {

    private Long inventoryId;
    private char size;
    private String color;
    private Integer stockLevel;
    private String SKU;

    public InventoryDto toDto() {
        return InventoryDto.builder()
                .inventoryId(this.inventoryId)
                .size(this.size)
                .color(this.color)
                .stockLevel(this.stockLevel)
                .SKU(this.SKU)
                .build();
    }
}