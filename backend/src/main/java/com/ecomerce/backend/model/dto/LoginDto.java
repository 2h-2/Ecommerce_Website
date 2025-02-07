package com.ecomerce.backend.model.dto;

import lombok.Data;

@Data
public class LoginDto {
    private long id;
    private String email;
    private String password;
}
