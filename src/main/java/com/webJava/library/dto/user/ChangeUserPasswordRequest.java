package com.webJava.library.dto.user;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ChangeUserPasswordRequest {

    @Pattern(regexp = "(?=\\w*[a-z])(?=\\w*[A-Z])(?=\\w*[0-9])\\w{8,}")
    private String oldPassword;

    @Pattern(regexp = "(?=\\w*[a-z])(?=\\w*[A-Z])(?=\\w*[0-9])\\w{8,}")
    private String newPassword;
}
