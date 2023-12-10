package com.webJava.library.service;

import com.webJava.library.dto.auth.LoginRequest;
import com.webJava.library.dto.auth.LoginResponse;
import com.webJava.library.dto.auth.RegisterRequest;

import java.io.IOException;

public interface AuthService {

    int register(RegisterRequest request) throws IOException;
    LoginResponse login(LoginRequest request);
}
