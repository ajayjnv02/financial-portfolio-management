package com.fpm.userservice.aws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String SESSION_PREFIX = "user:session:";

    public void saveSession(String sessionId, String username) {
        redisTemplate.opsForValue().set(SESSION_PREFIX + sessionId, username);
    }

    public String getSession(String sessionId) {
        return redisTemplate.opsForValue().get(SESSION_PREFIX + sessionId);
    }

    public void deleteSession(String sessionId) {
        redisTemplate.delete(SESSION_PREFIX + sessionId);
    }
}
