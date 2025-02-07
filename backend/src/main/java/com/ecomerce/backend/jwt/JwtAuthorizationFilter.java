package com.ecomerce.backend.jwt;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ecomerce.backend.config.CustomUserDetails;
import com.ecomerce.backend.model.entity.User;
import com.ecomerce.backend.repository.UserRepo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserRepo userRepo;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepo userRepo) {
        super(authenticationManager);
        this.userRepo = userRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader(JwtProperties.HEADER_STRING);

        
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response); // Continue the filter chain if no valid header
            return;
        }

        Authentication authentication = getUsernamePasswordAuthentication(request);
        System.out.println("///////////Header/////"+authentication);
        
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        

        chain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request)  {
        String token = request.getHeader(JwtProperties.HEADER_STRING);

        if (token != null && token.startsWith(JwtProperties.TOKEN_PREFIX)) {
            try {
                // Remove the token prefix
                token = token.replace(JwtProperties.TOKEN_PREFIX, "").trim();
                
                // Decode and verify the JWT token
                String email = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()))
                        .build()
                        .verify(token)
                        .getSubject();

                if (email != null) {
                    Optional<User> userOptional = userRepo.findByEmail(email);
                    if (userOptional.isPresent()) {
                        User user = userOptional.get();
                        UserDetails userDetails = new CustomUserDetails(user);
                        return new UsernamePasswordAuthenticationToken(
                                userDetails.getUsername(),
                                null,
                                userDetails.getAuthorities()
                        );
                    } else {
                        logger.warn("User not found for email: " + email);
                    }
                } else {
                    logger.warn("Failed to extract email from token.");
                }
            } catch (Exception e) {
 
                logger.error("Failed to verify JWT token: " + e.getMessage(), e);
            }
        }
        return null;
    }
}
