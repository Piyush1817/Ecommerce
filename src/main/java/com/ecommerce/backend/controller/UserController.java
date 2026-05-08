package com.ecommerce.backend.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.dto.LoginRequest;
import com.ecommerce.backend.dto.LoginResponse;
import com.ecommerce.backend.dto.UserDTO;
import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping
public List<UserDTO> getAllUsers() {
    return userService.getAllUsers();
}
@PostMapping("/login")
public LoginResponse login(@RequestBody LoginRequest request) {

    String token = userService.login(
            request.getEmail(),
            request.getPassword()
    );

    return new LoginResponse(token);
}
    @GetMapping("/me")
public String getLoggedInUser() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
}

@GetMapping("/admin")
public String adminAccess() {
    return "Welcome Admin!";
}

@GetMapping("/user")
public String userAccess() {
    return "Welcome User!";
}
}