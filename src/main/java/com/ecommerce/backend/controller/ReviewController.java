package com.ecommerce.backend.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.dto.ReviewDTO;
import com.ecommerce.backend.entity.Review;
import com.ecommerce.backend.service.ReviewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // ✅ Add Review
    @PostMapping("/{productId}/reviews")
    public ReviewDTO addReview(
            @PathVariable Long productId,
            @Valid @RequestBody ReviewDTO dto) {

          

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Review review = new Review();

        review.setUserEmail(email);
        review.setProductId(productId);
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());

        Review savedReview = reviewService.addReview(review);

        return convertToDTO(savedReview);
    }

    // ✅ Get Reviews Of Product
    @GetMapping("/{productId}/reviews")
    public List<ReviewDTO> getReviews(
            @PathVariable Long productId) {

        return reviewService.getReviewsByProduct(productId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // ✅ Get Average Rating
    @GetMapping("/{productId}/rating")
    public double getAverageRating(
            @PathVariable Long productId) {

        return reviewService.getAverageRating(productId);
    }

    // Helper Method
    private ReviewDTO convertToDTO(Review review) {

        ReviewDTO dto = new ReviewDTO();

        dto.setId(review.getId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());

        return dto;
    }
}