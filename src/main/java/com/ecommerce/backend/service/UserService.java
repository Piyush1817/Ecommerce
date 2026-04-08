package com.ecommerce.backend.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.backend.dto.UserDTO;
import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.repository.UserRepository;

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
    user.setPassword(passwordEncoder.encode(user.getPassword()));
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
public String login(String email,String password){
    User user= userRepository.findByEmail(email);

    if(user==null){
        return "User not found";


    }

    if(password==null){
        return "password is null";
    }
    if(passwordEncoder.matches(password,user.getPassword())){
        return "Login successful";

    } else {
        return "Invalid credentials";
    }
}

    
}