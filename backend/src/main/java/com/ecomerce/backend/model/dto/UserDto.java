package com.ecomerce.backend.model.dto;

import com.ecomerce.backend.model.entity.User;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String imageUrl;
    private String role;
    private String phone;

    public static UserDto toDto(User entity){
        return UserDto.builder()
        .id(entity.getId())
        .firstName(entity.getFirstName())
        .lastName(entity.getLastName())
        .email(entity.getEmail())
        .imageUrl(entity.getImageUrl())
        .role(entity.getRole())
        .address(entity.getAddress())
        .phone(entity.getPhone())
        .build();
    }

    public UserDto(User user){
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.address = user.getAddress();
        this.imageUrl = user.getImageUrl();
        this.role = user.getRole();
        this.phone = user.getPhone();
    }
}
