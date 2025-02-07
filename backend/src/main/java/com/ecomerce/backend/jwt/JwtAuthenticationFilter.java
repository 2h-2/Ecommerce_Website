package com.ecomerce.backend.jwt;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ecomerce.backend.model.dto.LoginDto;
import com.ecomerce.backend.model.dto.LoginResponse;
import com.ecomerce.backend.model.dto.UserDto;
import com.ecomerce.backend.model.entity.User;
import com.ecomerce.backend.repository.UserRepo;

@Service
public class JwtAuthenticationFilter {
    
    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private String generateToken(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String userName = userDetails.getUsername();
        
        String token =  JWT.create()
                .withSubject(userName)
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
        return token;
    }

    public LoginResponse login(LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        String token = generateToken(authentication);
        Optional<User> user = userRepo.findByEmail(loginDto.getEmail());
        return new LoginResponse(UserDto.toDto(user.get()), token);
    }

    /*public String signUp(UserDto userDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            userDto.getEmail(), userDto.getPassword()));
        
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepo.save(User.toEntity(userDto));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = generateToken(authentication);
        System.out.println(token);
        return token;
    }*/

}
