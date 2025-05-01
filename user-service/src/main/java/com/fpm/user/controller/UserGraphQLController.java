package com.fpm.user.controller;

import com.fpm.user.model.User;
import com.fpm.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UserGraphQLController {
    @Autowired
    private UserService userService;

    @QueryMapping
    public User getUser(@Argument Long id) {
        return userService.getUserById(id);
    }

    @MutationMapping
    public User registerUser(@Argument String name, @Argument String email, @Argument String password) {
        return userService.register(name, email, password);
    }
}
