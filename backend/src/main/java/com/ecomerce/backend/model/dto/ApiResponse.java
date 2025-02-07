package com.ecomerce.backend.model.dto;
import java.util.List;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private String status;
    private String message;
    private T data;

    public ApiResponse() {}


    public static <T> ApiResponse<T> of(String status, String message, T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setStatus(status);
        response.setMessage(message);
        response.setData(data);
        return response;
    }
}
