package com.fpm.user.graphql.resolver;

import com.fpm.user.entity.UserProfile;
import com.fpm.user.repository.UserProfileRepository;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class UserProfileResolver {

    @Autowired
    private UserProfileRepository userProfileRepository;

    public UserProfile getMyProfile(DataFetchingEnvironment env) {
        String email = extractEmailFromJwt(env);
        return userProfileRepository.findByEmail(email).orElse(null);
    }

    public UserProfile updateProfile(String fullName, String mobile, String address, DataFetchingEnvironment env) {
        String email = extractEmailFromJwt(env);
        UserProfile profile = userProfileRepository.findByEmail(email).orElse(new UserProfile());
        profile.setEmail(email);
        profile.setFullName(fullName);
      //  profile.setMobile(mobile);
        //profile.setAddress(address);
        return userProfileRepository.save(profile);
    }

    private String extractEmailFromJwt(DataFetchingEnvironment env) {
        JwtAuthenticationToken jwt = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return jwt.getToken().getClaimAsString("email");
    }
}
