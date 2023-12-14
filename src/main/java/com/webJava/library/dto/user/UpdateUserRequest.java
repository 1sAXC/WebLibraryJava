package com.webJava.library.dto.user;

import com.webJava.library.models.Image;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateUserRequest {
    private int role;

    private String username;

    private MultipartFile image;

    private String firstName;

    private String lastName;
}
