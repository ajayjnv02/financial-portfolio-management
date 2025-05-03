package com.fpm.user.controller;


import com.fpm.user.controller.dto.UpdateUserInput;
import com.fpm.user.entity.UserProfile;
import com.fpm.user.repository.UserProfileRepository;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserGraphQLController {

    private final UserProfileRepository userRepository;

    @QueryMapping
    public UserProfile getCurrentUser(DataFetchingEnvironment env, Principal principal) {
        String email = principal.getName(); // Usually preferred_username or sub from Keycloak token
        log.info("Fetching user by email: {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found for email: " + email));
    }

    @MutationMapping
    public UserProfile updateUserProfile(@Argument UpdateUserInput input, DataFetchingEnvironment env, Principal principal) {
        String email = principal.getName();
        log.info("Updating profile for email: {}", email);

        Optional<UserProfile> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found for email: " + email);
        }

        UserProfile user = optionalUser.get();
        user.setFullName(input.getFullName());
       // user.setMobile(input.getMobile());
        //user.setAddress(input.getAddress());
        return userRepository.save(user);
    }
}
