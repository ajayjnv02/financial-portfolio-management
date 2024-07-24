package com.fpm.userservice.aws.lambda.service;


import com.fpm.userservice.db.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserRegistrationFunction {

    @Autowired
    private UserService userService;

    @Bean
    public Function<User, String> registerUser() {
        return user -> {
            userService.registerUser(user);
            return "User registered successfully";
        };
    }
}