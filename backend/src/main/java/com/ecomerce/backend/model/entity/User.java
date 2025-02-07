package com.ecomerce.backend.model.entity;



import java.util.List;

import com.ecomerce.backend.model.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    private String password;
    private String address;
    @Column(name = "image_url")
    private String imageUrl;
    private String role;

    private String phone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Order> orders;

    public static User toEntity(UserDto dto){
        return User.builder()
        .id(dto.getId())
        .firstName(dto.getFirstName())
        .lastName(dto.getLastName())
        .email(dto.getEmail())
        .address(dto.getAddress())
        .imageUrl(dto.getImageUrl())
        .role(dto.getRole())
        .phone(dto.getPhone())
        .build();
    }

    /*private CartRepo cartRepo;
    public List<Cart> getCart(){
        long userId =  this.id;
        return cartRepo.findByUserId(userId);
    }*/
    

    
}
