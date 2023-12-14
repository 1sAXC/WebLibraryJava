package com.webJava.library.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateUserRequest {
    private String role;

    @Size(min = 3, max = 30)
    private String username;

    @Pattern(regexp = "(?=\\w*[a-z])(?=\\w*[A-Z])(?=\\w*[0-9])\\w{8,}")
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private MultipartFile avatar;
}
