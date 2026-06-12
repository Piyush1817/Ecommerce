package com.ecommerce.backend.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.dto.WishlistDTO;
import com.ecommerce.backend.entity.Wishlist;
import com.ecommerce.backend.service.WishlistService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(
            WishlistService wishlistService) {

        this.wishlistService = wishlistService;
    }

    // Add Item
    @PostMapping
    public WishlistDTO addToWishlist(
            @Valid @RequestBody WishlistDTO dto) {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        Wishlist wishlist = new Wishlist();

        wishlist.setUserEmail(email);
        wishlist.setProductId(dto.getProductId());

        Wishlist saved =
                wishlistService.addToWishlist(wishlist);

        WishlistDTO response = new WishlistDTO();

        response.setId(saved.getId());
        response.setProductId(saved.getProductId());

        return response;
    }

    // Get Wishlist
    @GetMapping
    public List<Wishlist> getWishlist() {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return wishlistService.getWishlist(email);
    }

    // Delete Item
    @DeleteMapping("/{id}")
    public String removeItem(
            @PathVariable Long id) {

        wishlistService.removeFromWishlist(id);

        return "Item removed from wishlist";
    }
}