package com.example.selfobservationrecord.service;

import com.example.selfobservationrecord.entity.User;
import com.example.selfobservationrecord.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean existsByUserId(String userId) {
        return userRepository.findByUserId(userId).isPresent();
    }

    public User createUser(String userId, String password) {
        User user = new User();
        
        LocalDateTime now = LocalDateTime.now();

        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(password));
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        return userRepository.save(user);
    }

    public User login(String userId, String password) {
        User user = userRepository.findByUserId(userId)
                .orElse(null);
    
        if (user == null) {
            return null;
        }
    
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return null;
        }
    
        return user;
    }
}