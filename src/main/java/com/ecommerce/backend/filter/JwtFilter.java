package com.ecommerce.backend.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String authHeader = req.getHeader("Authorization");

       
        String uri = req.getRequestURI();

        // ✅ Public APIs
        if (uri.contains("/login")
                || uri.contains("/register")
                || uri.contains("/swagger-ui")
                || uri.contains("/v3/api-docs")) {

            chain.doFilter(request, response);
            return;
        }

        // ✅ If token missing → continue
        // Spring Security will decide access
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            chain.doFilter(request, response);
            return;
        }

        try {

            String token = authHeader.substring(7);

            String email = JwtUtil.extractEmail(token);
            String role = JwtUtil.extractRole(token);



            // ✅ Create authentication object
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            List.of(new SimpleGrantedAuthority("ROLE_" + role))
                    );

            // ✅ Set authentication into SecurityContext
            SecurityContextHolder.getContext().setAuthentication(auth);

            // ✅ Admin protection
            
            if (uri.contains("/admin") && !role.equals("ADMIN")) {

                res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                res.getWriter().write("Access denied");

                return;
            }

        } catch (Exception e) {

            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("Invalid token");

            return;
        }

        // ✅ Continue request
        chain.doFilter(request, response);
    }
}