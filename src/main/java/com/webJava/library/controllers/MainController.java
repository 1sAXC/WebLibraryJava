package com.webJava.library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная страница");
        return "home";
    }
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "О нас");
        return "about";
    }

    @GetMapping("/catalog")
    public String catalog(Model model) {
        model.addAttribute("title", "Каталог книг");
        return "catalog";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        model.addAttribute("title", "Профиль");
        return "profile";
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
