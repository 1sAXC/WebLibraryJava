package com.webJava.library.controllers;

import org.springframework.ui.Model;
import com.webJava.library.security.JwtGenerator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.webJava.library.dto.auth.LoginRequest;
import com.webJava.library.dto.auth.LoginResponse;
import com.webJava.library.dto.auth.RegisterRequest;
import com.webJava.library.service.AuthService;

import java.io.IOException;

/**
 * Контроллер аутентификации и регистрации пользователей.
 */
@Controller
public class AuthController {
    private final AuthService authService;
    private final JwtGenerator jwt;

    @Autowired
    public AuthController(AuthService authService, JwtGenerator jwt) {
        this.authService = authService;
        this.jwt = jwt;
    }

    /**
     * Регистрирует нового пользователя.
     *
     * @param request объект запроса на регистрацию
     * @return код ответа и идентификатор зарегистрированного пользователя
     */
    @PostMapping("/regist")
    public ResponseEntity<Integer> register(@Valid @ModelAttribute RegisterRequest request) throws IOException {
        authService.register(request);
        return ResponseEntity.ok().header("redirect", "/login").body(0);
    }

    /**
     * Аутентифицирует пользователя.
     *
     * @param request объект запроса на аутентификацию
     * @return код ответа и информацию об аутентифицированном пользователе
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String login(@Valid LoginRequest request, HttpServletResponse response, Model model) {
        var res= authService.login(request);

        var cookie = new Cookie("AccessToken", res.getAccessToken());
        response.addCookie(cookie);
        return profile(res.getAccessToken(), model);
    }
    @RequestMapping(value = "profile", method = RequestMethod.GET)
    public String profile(@CookieValue("AccessToken") String token, Model model) {
        var username = jwt.getUsernameFromJwt(token);
        model.addAttribute("username", username);
        return "profile";
    }

}