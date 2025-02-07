package com.ecomerce.backend.error;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ApiError {
    private int status;
    private String message;
    private String path;
    private LocalDateTime timestamp;

    public ApiError(int status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }
}
