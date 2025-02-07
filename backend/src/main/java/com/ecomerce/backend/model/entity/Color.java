package com.ecomerce.backend.model.entity;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "colors")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public Color(Long id, String name){
        this.id = id;
        this.name = name;
    }
    
 
    @ManyToMany(mappedBy = "colors") 
    @JsonBackReference
    @JsonIgnore
    private Set<Product> products = new HashSet<>();
}
