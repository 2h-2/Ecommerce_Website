package com.ecomerce.backend.model.entity;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "sizes")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public Size(Long id, String name){
        this.id = id;
        this.name = name;
    }
    @ManyToMany(mappedBy = "sizes") 
    @JsonBackReference
    @JsonIgnore
    private Set<Product> products = new HashSet<>();
}
