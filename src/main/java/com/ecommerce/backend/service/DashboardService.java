package com.ecommerce.backend.service;

import org.springframework.stereotype.Service;

import com.ecommerce.backend.dto.DashboardDTO;
import com.ecommerce.backend.repository.OrderRepository;
import com.ecommerce.backend.repository.ProductRepository;
import com.ecommerce.backend.repository.UserRepository;

@Service
public class DashboardService {
   
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public DashboardService(
            UserRepository userRepository,
            ProductRepository productRepository,
            OrderRepository orderRepository) {

        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public DashboardDTO getDashboardStats() {

        DashboardDTO dto = new DashboardDTO();

        dto.setTotalUsers(userRepository.count());

        dto.setTotalProducts(productRepository.count());

        dto.setTotalOrders(orderRepository.count());

        dto.setPendingOrders(
                orderRepository.countByStatus("PENDING"));

        dto.setDeliveredOrders(
                orderRepository.countByStatus("DELIVERED"));

        return dto;
    }
}