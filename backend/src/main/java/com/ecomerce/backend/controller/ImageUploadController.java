package com.ecomerce.backend.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ecomerce.backend.service.FileUploadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@RestController
@RequestMapping("/api/images")
public class ImageUploadController {
 
    @Value("${upload.dir}")
    private String uploadDir;

    @Autowired
    private FileUploadService fileUploadService;

    /*@PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("productId") Long productId, @RequestParam("file") MultipartFile file) {
        
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select an image to upload.");
        }

        String fileName = fileUploadService.storeFile(productId,file);

        return ResponseEntity.ok("Image uploaded successfully: " + fileName);
    }*/
}
