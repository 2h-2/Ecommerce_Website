package com.ecomerce.backend.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ecomerce.backend.model.entity.User;


public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByRole(String role);
    Boolean existsByEmail(String email);
}
