package com.webJava.library.service;

import com.webJava.library.dto.auth.LoginRequest;
import com.webJava.library.dto.auth.LoginResponse;
import com.webJava.library.dto.auth.RegisterRequest;
import com.webJava.library.dto.user.CreateUserRequest;

import java.io.IOException;

public interface AuthService {

    int register(RegisterRequest request) throws IOException;
    int create(CreateUserRequest request) throws IOException;
    LoginResponse login(LoginRequest request);
}
