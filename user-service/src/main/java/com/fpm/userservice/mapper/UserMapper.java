package com.fpm.userservice.mapper;

import com.fpm.userservice.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {
    @Select("SELECT id, username, email, password_hash FROM users WHERE id = #{id}")
    User findById(Long id);

    @Select("SELECT id, username, email, password_hash FROM users WHERE username = #{username}")
    User findByUsername(String username);

    @Insert("INSERT INTO users(username, email, password_hash) VALUES(#{username}, #{email}, #{passwordHash})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    void insertUser(User user);

    @Update("UPDATE users SET email = #{email} WHERE id = #{id}")
    void updateEmail(@Param("id") Long id, @Param("email") String email);

    // Potentially more methods (e.g., delete, listAll, etc.)
}
