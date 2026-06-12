package com.ecommerce.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.backend.entity.Wishlist;
import com.ecommerce.backend.repository.WishlistRepository;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    public WishlistService(
            WishlistRepository wishlistRepository) {

        this.wishlistRepository = wishlistRepository;
    }

    // Add To Wishlist
    public Wishlist addToWishlist(Wishlist wishlist) {

        boolean alreadyExists =
                wishlistRepository
                        .findByUserEmailAndProductId(
                                wishlist.getUserEmail(),
                                wishlist.getProductId())
                        .isPresent();

        if (alreadyExists) {
            throw new RuntimeException(
                    "Product already in wishlist");
        }

        return wishlistRepository.save(wishlist);
    }

    // Get User Wishlist
    public List<Wishlist> getWishlist(String email) {

        return wishlistRepository.findByUserEmail(email);
    }

    // Remove Item
    public void removeFromWishlist(Long id) {

        wishlistRepository.deleteById(id);
    }
}