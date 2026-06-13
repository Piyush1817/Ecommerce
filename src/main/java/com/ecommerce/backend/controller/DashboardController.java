package com.ecommerce.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.dto.DashboardDTO;
import com.ecommerce.backend.service.DashboardService;

@RestController
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(
            DashboardService dashboardService) {

        this.dashboardService = dashboardService;
    }

    @GetMapping("/admin/dashboard")
    public DashboardDTO getDashboard() {

        return dashboardService.getDashboardStats();
    }
} 