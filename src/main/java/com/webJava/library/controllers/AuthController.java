package com.webJava.library.controllers;

import com.webJava.library.models.User;
import com.webJava.library.repository.UserRepository;
import com.webJava.library.service.StatusService;
import com.webJava.library.service.UserService;
import org.springframework.security.core.AuthenticationException;
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
import java.text.AttributedString;

/**
 * Контроллер аутентификации и регистрации пользователей.
 */
@Controller
public class AuthController {
    private final AuthService authService;

    private final UserService userService;
    private final StatusService statusService;
    private final JwtGenerator jwt;
    private AttributedString redirectAttributes;

    @Autowired
    public AuthController(AuthService authService, JwtGenerator jwt, UserService userService, StatusService statusService) {
        this.authService = authService;
        this.jwt = jwt;
        this.userService = userService;
        this.statusService = statusService;
    }

    /**
     * Регистрирует нового пользователя.
     *
     * @param request объект запроса на регистрацию
     * @return код ответа и идентификатор зарегистрированного пользователя
     */
    @PostMapping("/regist")
    public String register(@Valid @ModelAttribute RegisterRequest request, HttpServletResponse response, Model model) throws IOException {
        var register = authService.register(request);

        var res = authService.login(new LoginRequest(request.getUsername(), request.getPassword()));
        statusService.addVisitor();
        var cookie = new Cookie("AccessToken", res.getAccessToken());
        response.addCookie(cookie);
        return profile(res.getAccessToken(), model);
        //
        // return ResponseEntity.ok().header("redirect", "/login").body(0);
    }

    /**
     * Аутентифицирует пользователя.
     *
     * @param request объект запроса на аутентификацию
     * @return код ответа и информацию об аутентифицированном пользователе
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String login(@Valid LoginRequest request, HttpServletResponse response, Model model) {
        try{
            var res= authService.login(request);
            statusService.addVisitor();
            var cookie = new Cookie("AccessToken", res.getAccessToken());
            response.addCookie(cookie);
            return profile(res.getAccessToken(), model);
        }
        catch(AuthenticationException e) {
            return "redirect:/login?error";
        }

    }
    @RequestMapping(value = "profile", method = RequestMethod.GET)
    public String profile(@CookieValue(value = "AccessToken", required = false) String token, Model model) {
        if (token == null)
        {
            return "login";
        }
        var username = jwt.getUsernameFromJwt(token);
        var user = userService.getUserByName(username);
        model.addAttribute("user", user);
        model.addAttribute("role", user.getRoleId());
        return "profile";
    }


}