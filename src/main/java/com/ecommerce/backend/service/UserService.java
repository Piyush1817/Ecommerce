package com.ecommerce.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.backend.dto.UserDTO;
import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.util.JwtUtil;

@Service
public class UserService {

    private final UserRepository userRepository;

    
private final BCryptPasswordEncoder passwordEncoder;

public UserService(UserRepository userRepository,
                   BCryptPasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
}
    public User createUser(User user) {

    if (userRepository.findByEmail(user.getEmail()).isPresent()) {
        throw new RuntimeException("Email already exists");
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole("USER");

    return userRepository.save(user);
}

    public List<UserDTO> getAllUsers() {
    List<User> users = userRepository.findAll();

    return users.stream().map(user ->
        new UserDTO(
            user.getId(),
            user.getName(),
            user.getEmail()
        )
    ).toList();
}
public String login(String email, String password) {
    Optional<User> userOptional = userRepository.findByEmail(email);

    // ✅ Check if user exists
    if (userOptional.isEmpty()) {
        return "User not found";
    }

    User user = userOptional.get();

    // ✅ Check password
     if (passwordEncoder.matches(password, user.getPassword())) {
    return JwtUtil.generateToken(
        user.getEmail(),
        user.getRole()
    );
}
    else {
        return "Invalid password";
    }
}
    
}