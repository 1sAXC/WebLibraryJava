package com.webJava.library.dto.auth;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {
    @Size(min = 0, max = 100000)
    private String username;

    @Pattern(regexp = "^.*$")
    private String password;

    public LoginRequest(String username, String password) {
        this.password = password;
        this.username = username;
    }
}
