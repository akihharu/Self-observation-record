package com.example.selfobservationrecord.controller;

import com.example.selfobservationrecord.dto.UserSignupRequest;
import com.example.selfobservationrecord.entity.User;
import com.example.selfobservationrecord.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.selfobservationrecord.dto.UserLoginRequest;
import com.example.selfobservationrecord.dto.UserLoginResponse;
import org.springframework.web.server.ResponseStatusException;

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

    @PostMapping("/login")
    public UserLoginResponse login(@Valid @RequestBody UserLoginRequest request) {
    
        User user = userService.login(
                request.getUserId(),
                request.getPassword()
        );
    
        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid user ID or password"
            );
        }
    
        return new UserLoginResponse(user.getUserId());
    }
}