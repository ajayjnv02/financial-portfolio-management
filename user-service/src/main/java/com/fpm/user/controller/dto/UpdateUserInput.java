package com.fpm.user.controller.dto;


import lombok.Data;

@Data
public class UpdateUserInput {
    private String fullName;
    private String mobile;
    private String address;
}
