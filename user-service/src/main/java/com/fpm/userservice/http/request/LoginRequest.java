package com.fpm.userservice.http.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;


}
