package com.ecommerce.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.backend.entity.Cart;
import com.ecommerce.backend.repository.CartRepository;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    // ✅ Add to cart
    public Cart addToCart(Cart cart) {
        return cartRepository.save(cart);
    }

    // ✅ Get user cart
    public List<Cart> getUserCart(String email) {
        return cartRepository.findByUserEmail(email);
    }

    // ✅ Delete item
    public void removeFromCart(Long id) {
        cartRepository.deleteById(id);
    }
}