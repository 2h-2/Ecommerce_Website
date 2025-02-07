package com.ecomerce.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import com.ecomerce.backend.jwt.JwtAuthorizationFilter;
import com.ecomerce.backend.repository.UserRepo;

@Component
public class SecurityConfig {
        
    private UserRepo userRepo;

    public SecurityConfig( UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests((authorize) ->
                        //authorize.anyRequest().authenticated()
                        authorize.requestMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/user/**").permitAll()
                                .requestMatchers("/api/product/**").hasRole("ROLE_ADMIN")
                                .requestMatchers("/api/order/**").hasRole("ROLE_ADMIN")
                                .requestMatchers("/api/category/**").hasRole("ROLE_ADMIN")
                                .requestMatchers("/api/cart/**").hasRole("ROLE_USER")
                                .requestMatchers("/error").permitAll()
                                .requestMatchers("/upload").permitAll()
                                .requestMatchers("/static/**", "/images/**").permitAll()
                                .anyRequest().authenticated()

        )
        .addFilter(new JwtAuthorizationFilter(authenticationManager,userRepo));;

        return http.build();
    }
}
