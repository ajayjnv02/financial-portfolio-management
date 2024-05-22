package com.fpm.userservice.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;
    private String updatedAt;

}
