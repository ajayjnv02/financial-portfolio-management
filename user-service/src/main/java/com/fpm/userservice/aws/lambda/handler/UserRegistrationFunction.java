package com.fpm.userservice.aws.lambda.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fpm.userservice.db.entity.User;
import com.fpm.userservice.db.repository.UserRepository;


public class UserRegistrationFunction implements RequestHandler<User, String> {

    private final UserRepository userRepository = new UserRepository();

    @Override
    public String handleRequest(User user, Context context) {
        // Save user to DynamoDB
        userRepository.save(user);
        return "User registered successfully: " + user.getUsername();
    }
}
