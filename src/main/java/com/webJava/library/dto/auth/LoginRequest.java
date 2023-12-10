package com.webJava.library.dto.auth;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {
    @Size(min = 3, max = 30)
    private String username;

    @Pattern(regexp = "(?=\\w*[a-z])(?=\\w*[A-Z])(?=\\w*[0-9])\\w{8,}")
    private String password;

    public LoginRequest(String username, String password) {
        this.password = password;
        this.username = username;
    }
}
