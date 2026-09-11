package com.example.selfobservationrecord.service;

import com.example.selfobservationrecord.entity.User;
import com.example.selfobservationrecord.repository.UserRepository;
import org.springframework.stereotype.Service;

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
        user.setUserId(userId);
        user.setPassword(password);

        return userRepository.save(user);
    }
}