package com.fpm.userservice.model;

public record User(
        Long id,
        String username,
        String email,
        String passwordHash
) {}
