package com.fpm.user.graphql;

import com.fpm.user.model.User;
import com.fpm.user.service.UserService;
import com.netflix.graphql.dgs.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@DgsComponent
@Slf4j
public class UserDataFetcher {

    @Autowired
    private UserService userService;

    @DgsQuery
    public User getUser(@InputArgument Long id) {
        return userService.getUserById(id);
    }

    @DgsMutation
    public User registerUser(@InputArgument String name, @InputArgument String email, @InputArgument String password) {
      log.info("Register user {}",name);

        return userService.register(name, email, password);
    }
}
