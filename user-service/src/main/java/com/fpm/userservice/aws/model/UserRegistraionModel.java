package com.fpm.userservice.aws.model;

public class UserRegistraionModel {
    public record RegistrationRequest(String username, String passwordHash) {}
    public record RegistrationResponse(boolean success, String message) {}

}
