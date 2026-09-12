package com.example.selfobservationrecord.controller;

import com.example.selfobservationrecord.dto.UserSignupRequest;
import com.example.selfobservationrecord.entity.User;
import com.example.selfobservationrecord.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public User signup(@Valid @RequestBody UserSignupRequest request) {

        if (userService.existsByUserId(request.getUserId())) {
            throw new IllegalArgumentException("User ID already exists");
        }

        return userService.createUser(
                request.getUserId(),
                request.getPassword()
        );
    }
}