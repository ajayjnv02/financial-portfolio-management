package com.fpm.user.graphql;

import com.fpm.user.model.User;
import com.fpm.user.repository.UserRepository;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsEntityFetcher;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
@Slf4j
@DgsComponent
public class UserResolver {

    private final UserRepository userRepository;

    public UserResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @DgsEntityFetcher(name = "User")
    public User resolve(Map<String, Object> values) {
        log.info("Resolving user {}",values);
        Long id = Long.parseLong(values.get("id").toString());
        return userRepository.findById(id).orElse(null);
    }
}
