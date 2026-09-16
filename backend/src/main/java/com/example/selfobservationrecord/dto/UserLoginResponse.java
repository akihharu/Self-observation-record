package com.example.selfobservationrecord.dto;

public class UserLoginResponse {

    private String userId;

    public UserLoginResponse(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}