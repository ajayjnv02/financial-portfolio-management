package com.fpm.userservice.controller;

import com.fpm.userservice.aws.lambda.service.UserRegisterLambdaService;
import com.fpm.userservice.db.entity.User;
import com.fpm.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRegisterLambdaService userRegisterLambdaService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
  
        User existingUser = userService.findByEmail(user.getEmail());
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }
        user.setCreatedAt(LocalDateTime.now());
        String response = userRegisterLambdaService.invokeRegisterUserLambda(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
