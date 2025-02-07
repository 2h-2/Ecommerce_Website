package com.ecomerce.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import com.ecomerce.backend.model.dto.ProductDto;
import com.ecomerce.backend.model.entity.Product;
import com.ecomerce.backend.service.ProductService;
import com.ecomerce.backend.service.ProductService.productResponse;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/product")
public class ProductController {
    
    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(
        @Valid  @RequestPart("product") ProductDto productRequest,
        BindingResult bindingResult,
        @RequestParam("images") MultipartFile[] images) {

            if (bindingResult.hasErrors()) {
                return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
            }
            if (images == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Images are required");
            }
            try {
                System.out.println("Product Details: " + productRequest);
                productService.addProduct(productRequest, images);
                return ResponseEntity.ok("Product added successfully!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add product: " + e.getMessage());
            }
    }

    
    @GetMapping("/list")
    public ResponseEntity<List<productResponse>> getAllProducts(){
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<productResponse> getProduct(@RequestParam("productId") Long id){
        return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
    }

    @GetMapping("/getpro")
    public ResponseEntity<?> getPro(@RequestParam("productId") Long id){
        return new ResponseEntity<>(productService.getPro(id), HttpStatus.OK);
    }

    @GetMapping("/del")
    public ResponseEntity<?> delete(@RequestParam("productId") Long id){
        productService.delete(id);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }

}

/*
 
@PostMapping("/add")
    public ResponseEntity<?> addProduct(
        @Valid @ModelAttribute ProductDto productRequest,
        BindingResult bindingResult,
        @RequestParam("image") MultipartFile image) {

            if (bindingResult.hasErrors()) {
                return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
            }
            if (image.isEmpty()) {
                return ResponseEntity.badRequest().body("Image file is required.");
            }
            try {

                System.out.println("Product Details: " + productRequest);
                System.out.println("Image Original Filename: " + image.getOriginalFilename());
                productService.addProduct(productRequest, image);
                return ResponseEntity.ok("Product added successfully!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add product: " + e.getMessage());
            }
    }

    @GetMapping("/get")
    public ResponseEntity<productResponse> getProduct(@RequestParam("productId") Long id){
        return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
    }

    @GetMapping("/getpro")
    public ResponseEntity<Product> getProductByid(@RequestParam("productId") Long id){
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }
    
    @GetMapping("/list")
    public ResponseEntity<List<productResponse>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProduct(), HttpStatus.OK);
    }

    @GetMapping("/test")
    public String test(){
        return productService.test();
    }

*/
