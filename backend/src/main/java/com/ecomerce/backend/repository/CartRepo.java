package com.ecomerce.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ecomerce.backend.model.entity.Cart;
import org.springframework.stereotype.Repository;


@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {
    List<Cart> findByUserId(Long id);
    //Cart findByUserId(Long id);
    boolean existsByProductIdAndColorIdAndSizeIdAndUserId(Long productId,Long colorId,Long sizeId, Long userId);
    Cart findByProductIdAndColorIdAndSizeIdAndUserId(Long productId,Long colorId,Long sizeId, Long userId);
    //@Query("SELECT COUNT(c) FROM Cart c WHERE c.userId = :userId")
    //long countByUserId(@Param("userId") Long userId);
    long countByUserId(Long userId);
    
}

