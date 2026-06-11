package com.ecommerce.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.backend.entity.Review;

public interface ReviewRepository
        extends JpaRepository<Review, Long> {

    List<Review> findByProductId(Long productId);

    boolean existsByUserEmailAndProductId(
            String userEmail,
            Long productId);
}   