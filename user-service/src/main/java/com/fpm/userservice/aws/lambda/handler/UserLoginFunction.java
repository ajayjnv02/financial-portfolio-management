package com.fpm.userservice.aws.lambda.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fpm.userservice.db.entity.User;
import com.fpm.userservice.db.repository.UserRepository;
import com.fpm.userservice.http.request.LoginRequest;


public class UserLoginFunction implements RequestHandler<LoginRequest, String> {

    private final UserRepository userRepository = new UserRepository();

    @Override
    public String handleRequest(LoginRequest loginRequest, Context context) {
        User user = userRepository.findByUsername(loginRequest.getUsername());
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            return "Login successful for user: " + loginRequest.getUsername();
        }
        return "Invalid credentials";
    }
}
