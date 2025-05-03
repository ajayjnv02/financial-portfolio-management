package com.fpm.user.controller;

import com.fpm.user.entity.UserProfile;
import com.fpm.user.service.UserProfileService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserProfileService service;

    public UserController(UserProfileService service) {
        this.service = service;
    }

    @GetMapping("/me")
    public UserProfile getCurrentUser(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        return service.getUserProfile(userId)
                .orElseGet(() -> {
                    UserProfile profile = UserProfile.builder()
                            .id(userId)
                            .email(jwt.getClaimAsString("email"))
                            .fullName(jwt.getClaimAsString("name"))
                            .build();
                    return service.createOrUpdateUserProfile(profile);
                });
    }
}
