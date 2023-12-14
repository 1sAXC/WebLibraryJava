package com.webJava.library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная страница");
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

    public String handleWebSocketMessage(String message) {
        // Обработка сообщения от клиента
        return "Received message: " + message;
    }

}
