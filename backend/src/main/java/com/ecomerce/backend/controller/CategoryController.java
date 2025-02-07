package com.ecomerce.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecomerce.backend.model.dto.ApiResponse;
import com.ecomerce.backend.model.dto.CartDto;
import com.ecomerce.backend.model.dto.CategoryDto;
import com.ecomerce.backend.service.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addCart(@RequestPart("category") CategoryDto catg){
        categoryService.add(catg);
        ApiResponse<String> response = ApiResponse.of(
                "success",
                "Added successfully",
                null
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
