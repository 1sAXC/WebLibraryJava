package com.webJava.library.dto.user;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ChangeUserPasswordRequest {

    @Pattern(regexp = "^.*$")
    private String oldPassword;

    @Pattern(regexp = "^.*$")
    private String newPassword;
}
