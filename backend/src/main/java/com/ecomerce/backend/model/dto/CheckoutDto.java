package com.ecomerce.backend.model.dto;

//import jakarta.validation.constraints.*;

import java.math.BigDecimal;


import lombok.*;

@Data
public class CheckoutDto {
    private String name;
    //@Email(message = "Invalid email format.")
    private String email;
    private String phone;
    private String address;
    private String city;
    private BigDecimal total;
    private String expiry;     
    private String cvv;
}
