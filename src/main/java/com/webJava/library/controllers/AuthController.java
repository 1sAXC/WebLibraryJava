package com.webJava.library.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.webJava.library.dto.auth.LoginRequest;
import com.webJava.library.dto.auth.LoginResponse;
import com.webJava.library.dto.auth.RegisterRequest;
import com.webJava.library.service.AuthService;

import java.io.IOException;

/**
 * Контроллер аутентификации и регистрации пользователей.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Регистрирует нового пользователя.
     *
     * @param request объект запроса на регистрацию
     * @return код ответа и идентификатор зарегистрированного пользователя
     */
    @PostMapping("/regist")
    public ResponseEntity<Integer> register(@Valid @ModelAttribute RegisterRequest request) throws IOException {
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * Аутентифицирует пользователя.
     *
     * @param request объект запроса на аутентификацию
     * @return код ответа и информацию об аутентифицированном пользователе
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}