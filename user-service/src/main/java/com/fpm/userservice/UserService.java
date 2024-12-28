package com.fpm.userservice;

import com.fpm.userservice.mapper.UserMapper;
import com.fpm.userservice.model.User;
import com.fpm.userservice.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {

    public void registerUser(String username, String rawPassword, String email) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);

            // Check if username taken
            User existing = mapper.findByUsername(username);
            if (existing != null) {
                throw new RuntimeException("Username already exists: " + username);
            }

            // Hash password
            String hashed = BCrypt.hashpw(rawPassword, BCrypt.gensalt());

            // Insert new user
            User user = new User(null, username, email, hashed);
            mapper.insertUser(user);

            // Must commit changes if you opened session manually
            session.commit();
        }
    }

    public String loginUser(String username, String rawPassword) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.findByUsername(username);
            if (user == null || !BCrypt.checkpw(rawPassword, user.passwordHash())) {
                throw new RuntimeException("Invalid credentials");
            }
            // For demonstration, we just return a simple token
            return "TOKEN-" + user.username() + "-" + System.currentTimeMillis();
        }
    }

    public User getUserById(Long userId) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.findById(userId);
            if (user == null) {
                throw new RuntimeException("User not found: " + userId);
            }
            return user;
        }
    }

    public void updateUserEmail(Long userId, String newEmail) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.updateEmail(userId, newEmail);
            session.commit();
        }
    }
}
