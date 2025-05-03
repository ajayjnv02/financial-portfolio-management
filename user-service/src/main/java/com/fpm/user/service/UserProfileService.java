package com.fpm.user.service;

import com.fpm.user.entity.UserProfile;
import com.fpm.user.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService {

    private final UserProfileRepository repository;

    public UserProfileService(UserProfileRepository repository) {
        this.repository = repository;
    }

    public Optional<UserProfile> getUserProfile(String id) {
        return repository.findById(id);
    }

    public UserProfile createOrUpdateUserProfile(UserProfile profile) {
        return repository.save(profile);
    }
}

