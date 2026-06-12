package com.ecommerce.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.backend.entity.Review;
import com.ecommerce.backend.repository.ReviewRepository;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    // ✅ Add Review
    public Review addReview(Review review) {

        boolean alreadyReviewed =
                reviewRepository.existsByUserEmailAndProductId(
                        review.getUserEmail(),
                        review.getProductId()
                );

        if (alreadyReviewed) {
            throw new RuntimeException(
                    "You have already reviewed this product"
            );
        }

        return reviewRepository.save(review);
    }

    // ✅ Get All Reviews Of A Product
    public List<Review> getReviewsByProduct(Long productId) {

        return reviewRepository.findByProductId(productId);
    }

    // ✅ Calculate Average Rating
    public double getAverageRating(Long productId) {

        List<Review> reviews =
                reviewRepository.findByProductId(productId);

        if (reviews.isEmpty()) {
            return 0.0;
        }

        double total = 0;

        for (Review review : reviews) {
            total += review.getRating();
        }

        return total / reviews.size();
    }
}