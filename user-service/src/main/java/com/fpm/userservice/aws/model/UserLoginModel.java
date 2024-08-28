package com.fpm.userservice.aws.model;

public class UserLoginModel {

    public record LoginRequest(String username, String passwordHash) {
    }

    public record LoginResponse(boolean success, String message,String sessionId) {
    }
}
