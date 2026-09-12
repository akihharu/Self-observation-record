package com.example.selfobservationrecord.service;

import com.example.selfobservationrecord.entity.User;
import com.example.selfobservationrecord.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean existsByUserId(String userId) {
        return userRepository.findByUserId(userId).isPresent();
    }

    public User createUser(String userId, String password) {
        User user = new User();
        
        LocalDateTime now = LocalDateTime.now();

        user.setUserId(userId);
        user.setPassword(password);
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        return userRepository.save(user);
    }
}