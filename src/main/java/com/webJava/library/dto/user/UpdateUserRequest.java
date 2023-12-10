package com.webJava.library.dto.user;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateUserRequest {
    private int roleId;

    private String username;

    private String password;

    private MultipartFile image;

    private String firstName;

    private String lastName;
}
