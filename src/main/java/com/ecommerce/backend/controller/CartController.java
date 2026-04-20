package com.ecommerce.backend.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.entity.Cart;
import com.ecommerce.backend.service.CartService;

@RestController
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // 🟢 USER: Add item to cart
    @PostMapping("/cart")
    public Cart addToCart(@RequestBody Cart cart) {
       // 🔐 Get email from JWT (not from request)
    String email = SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getName();

    cart.setUserEmail(email);

    return cartService.addToCart(cart);
    }

    // 🟢 USER: Get cart items
    @GetMapping("/cart")
    public List<Cart> getCart() {
        String email = SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getName();

    return cartService.getUserCart(email);
    }

    // 🟢 USER: Remove item
    @DeleteMapping("/cart/{id}")
    public String deleteItem(@PathVariable Long id) {
        cartService.removeFromCart(id);
        return "Item removed";
    }
}