package com.ecomerce.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.ecomerce.backend.model.dto.LoginDto;
import com.ecomerce.backend.model.dto.LoginResponse;
import com.ecomerce.backend.model.entity.User;
import com.ecomerce.backend.repository.UserRepo;
import com.ecomerce.backend.jwt.JwtAuthenticationFilter;


@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/auth")
public class AuthController {
    
    //@Autowired
    //private AuthenticationManager authenticationManager;


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    private JwtAuthenticationFilter jwtAuthenticationFilter ;
    public AuthController(JwtAuthenticationFilter jwtAuthenticationFilter){
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginDto loginDto){
        LoginResponse response = jwtAuthenticationFilter.login(loginDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user){

        if(userRepo.existsByEmail(user.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }
//        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        
        return new ResponseEntity<>("Registration Success.", HttpStatus.OK);

    }

}
