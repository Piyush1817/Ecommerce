package com.ecommerce.backend.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.dto.OrderDTO;
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
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return orderService.placeOrder(email);
    }

    // 🟢 USER → VIEW OWN ORDERS
    @GetMapping("/orders")
public List<OrderDTO> getUserOrders() {

    String email = SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getName();

    return orderService.getUserOrders(email)
            .stream()
            .map(this::convertToDTO)
            .toList();
}

    // 🔴 ADMIN → VIEW ALL ORDERS
    @GetMapping("/admin/orders")
public List<OrderDTO> getAllOrders() {

    return orderService.getAllOrders()
            .stream()
            .map(this::convertToDTO)
            .toList();
}

   @PutMapping("/admin/orders/{id}/status")
public OrderDTO updateOrderStatus(
        @PathVariable Long id,
        @RequestParam String status) {

    Order order =
            orderService.updateOrderStatus(id, status);

    return convertToDTO(order);
}

private OrderDTO convertToDTO(Order order) {

    OrderDTO dto = new OrderDTO();

    dto.setId(order.getId());
    dto.setProductId(order.getProductId());
    dto.setQuantity(order.getQuantity());
    dto.setStatus(order.getStatus());

    return dto;
}
}