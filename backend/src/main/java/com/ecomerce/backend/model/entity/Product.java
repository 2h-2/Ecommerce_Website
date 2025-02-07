package com.ecomerce.backend.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import com.ecomerce.backend.model.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


import jakarta.persistence.*;
import lombok.*;

@Table(name = "products")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    private String material;
    private String style;

    @Column(name= "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name= "unit_price", nullable = false)
    private BigDecimal unitPrice;

    private Double discount;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @JsonBackReference
    private Category category;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ProductImages> images;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "product_size",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "size_id")
    )
    @JsonManagedReference
    private List<Size> sizes =  new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "product_color",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "color_id")
    )
    @JsonManagedReference
    private List<Color> colors = new ArrayList<>();


    
    public static Product toEntity(ProductDto dto){

        return Product.builder()
        .name(dto.getName())
        .description(dto.getDescription())
        .unitPrice(dto.getUnitPrice())
        .style(dto.getStyle())
        .material(dto.getMaterial())
        .discount(dto.getDiscount())
        .build();
    }

}
