package com.webJava.library.dto.user;

import org.springframework.web.multipart.MultipartFile;

public class CreateUserRequest {
    private int roleId;

    private String username;

    private String password;

    private MultipartFile image;

    private String firstName;

    private String lastName;
}
