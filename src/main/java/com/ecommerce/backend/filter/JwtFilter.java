package com.ecommerce.backend.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.ecommerce.backend.util.JwtUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String authHeader = req.getHeader("Authorization");
        System.out.println("Auth Header: " + authHeader);

        // 🔐 Allow login without token
        if (req.getRequestURI().contains("/login")) {
            chain.doFilter(request, response);
            return;
        }

        // ❌ No token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("Missing or invalid token");
            return;
        }

        try {
            String token = authHeader.substring(7);
            String email = JwtUtil.extractEmail(token);

            System.out.println("Authenticated user: " + email);

        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("Invalid token");
            return;
        }

        // ✅ Continue if valid
        chain.doFilter(request, response);
    }
}