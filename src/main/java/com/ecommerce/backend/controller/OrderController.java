package com.ecommerce.backend.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.entity.Order;
import com.ecommerce.backend.service.OrderService;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 🟢 USER → PLACE ORDER
    @PostMapping("/order")
    public String placeOrder() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return orderService.placeOrder(email);
    }

    // 🟢 USER → VIEW OWN ORDERS
    @GetMapping("/orders")
    public List<Order> getUserOrders() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return orderService.getUserOrders(email);
    }

    // 🔴 ADMIN → VIEW ALL ORDERS
    @GetMapping("/admin/orders")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
}