package com.fpm.userservice.repository;


import com.fpm.userservice.model.User;

import java.sql.*;

public class UserRepository {

    private final String url;
    private final String dbUser;
    private final String dbPassword;

    public UserRepository(String url, String dbUser, String dbPassword) {
        this.url = url;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    /**
     * Creates a new user in the database.
     */
    public void saveUser(User user) throws SQLException {
        String insertSQL = "INSERT INTO users (username, email, password_hash) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(insertSQL)) {

            stmt.setString(1, user.username());
            stmt.setString(2, user.email());
            stmt.setString(3, user.passwordHash());
            stmt.executeUpdate();
        }
    }

    /**
     * Finds a user by username.
     */
    public User findByUsername(String username) throws SQLException {
        String querySQL = "SELECT id, username, email, password_hash FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(querySQL)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password_hash")
                );
            }
            return null;
        }
    }

    /**
     * Finds a user by ID.
     */
    public User findById(long userId) throws SQLException {
        String querySQL = "SELECT id, username, email, password_hash FROM users WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(querySQL)) {

            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password_hash")
                );
            }
            return null;
        }
    }

    /**
     * Updates user information (example: updating email).
     */
    public void updateEmail(long userId, String newEmail) throws SQLException {
        String updateSQL = "UPDATE users SET email = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(updateSQL)) {
            stmt.setString(1, newEmail);
            stmt.setLong(2, userId);
            stmt.executeUpdate();
        }
    }
}
