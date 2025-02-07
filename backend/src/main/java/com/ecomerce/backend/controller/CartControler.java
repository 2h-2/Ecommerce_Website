package com.ecomerce.backend.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecomerce.backend.model.dto.ApiResponse;
import com.ecomerce.backend.model.dto.CartDto;
import com.ecomerce.backend.model.dto.CartResponse;
import com.ecomerce.backend.model.dto.CheckoutDto;

import com.ecomerce.backend.service.CartService;

@CrossOrigin(origins = "http://localhost:4200") // Allow requests from the frontend URL

@RestController
@RequestMapping("/api/cart")
public class CartControler {
    
      @Autowired
    CartService cartService;

   
    
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addCart(@RequestBody CartDto cart){
        cartService.save(cart);
        ApiResponse<String> response = ApiResponse.of(
                "success",
                "Added successfully",
                null
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/count/{userId}")
    public ResponseEntity<ApiResponse<Map<String,Long>>> getCartItemCountForUser(@PathVariable Long userId) {
        long count = cartService.getCartItemCountForUser(userId);
        
        Map<String, Long> data = new HashMap<>();
        data.put("count", count);

        ApiResponse<Map<String,Long>> response = ApiResponse.of(
                "success",
                "Data retrieved successfully",
                data
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<CartResponse>> getCartByUser(@PathVariable("id") Long id){
        
        return new ResponseEntity<>(cartService.getCartByUser(id), HttpStatus.OK);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCart(@PathVariable("id") Long id){
        boolean exists = cartService.isCartExist(id);

        if (!exists) {
            throw new NoSuchElementException("ID '" + id + "' is invalid");
        }
        
        cartService.delete(id);

        ApiResponse<String> response = ApiResponse.of(
                "success",
                "Deleted successfully",
                null
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/checkout/{userId}")
    public ResponseEntity<ApiResponse<String>> checkout(@PathVariable("userId") Long id,@RequestBody CheckoutDto dto){
        if(cartService.getCartItemCountForUser(id) == 0){
            throw new IllegalArgumentException("No cart to this user !");
        }

        cartService.checkout(id, dto);
        ApiResponse<String> response = ApiResponse.of(
                "success",
                "success",
                null
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    /*

    /*@GetMapping("/getAll")
    public ResponseEntity<List<Cart>> getAllUsers(){
        return new ResponseEntity<>(cartService.getAllCarts(), HttpStatus.OK);
    }*/

}
