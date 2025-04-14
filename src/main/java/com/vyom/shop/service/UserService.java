package com.vyom.shop.service;

import com.vyom.shop.model.entity.User;
import com.vyom.shop.model.request.UserRequest;
import com.vyom.shop.model.response.UserResponse;
import com.vyom.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse registerUser(UserRequest userRequest) {
        // Check if username already exists
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Create new User object and set fields
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword())); // Encrypt password

        // Save the user to the database
        User savedUser = userRepository.save(user);

        // Prepare response
        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setUsername(savedUser.getUsername());
        response.setMessage("User registered successfully");
        return response;
    }
}
