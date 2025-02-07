package com.ecomerce.backend.model.dto;

import lombok.*;

@Data
@AllArgsConstructor

public class LoginResponse {
    private UserDto user;
    private String jwt;
}
