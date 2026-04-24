package com.ecommerce.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.backend.entity.Cart;
import com.ecommerce.backend.entity.Order;
import com.ecommerce.backend.repository.CartRepository;
import com.ecommerce.backend.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    public OrderService(OrderRepository orderRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }

    // 🔥 PLACE ORDER
    public String placeOrder(String userEmail) {

        List<Cart> cartItems = cartRepository.findByUserEmail(userEmail);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // Convert cart → order
        for (Cart item : cartItems) {
            Order order = new Order(
                    userEmail,
                    item.getProductId(),
                    item.getQuantity()
            );

            orderRepository.save(order);
        }

        // Clear cart
        cartRepository.deleteAll(cartItems);

        return "Order placed successfully!";
    }

    // 🔥 USER ORDER HISTORY
    public List<Order> getUserOrders(String userEmail) {
        return orderRepository.findByUserEmail(userEmail);
    }

    // 🔥 ADMIN → ALL ORDERS
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}