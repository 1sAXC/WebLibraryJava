package com.webJava.library.controllers;

import com.webJava.library.security.JwtGenerator;
import com.webJava.library.service.StatusService;
import com.webJava.library.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final UserService userService;
    private final StatusService statusService;
    private final JwtGenerator jwt;
    public MainController(UserService userService, JwtGenerator jwt, StatusService statusService){
        this.userService = userService;
        this.statusService = statusService;
        this.jwt = jwt;
    }
    @GetMapping("/")
    public String home(Model model, @CookieValue("AccessToken") String token) {
        model.addAttribute("title", "Главная страница");
        model.addAttribute("count", statusService.getVisitors());
        return "home";
    }


    @GetMapping("/regist")
    public String regist(Model model) {
        model.addAttribute("title", "Регистрация");
        return "regist";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Регистрация");
        return "login";
    }


}
