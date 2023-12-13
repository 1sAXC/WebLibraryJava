package com.webJava.library.controllers;

import com.webJava.library.dto.auth.LoginRequest;
import com.webJava.library.dto.auth.RegisterRequest;
import com.webJava.library.dto.book.CreateBookRequest;
import com.webJava.library.service.BookService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class AdminController {

    private final BookService bookService;
    public AdminController(BookService bookService){
        this.bookService = bookService;
    }
    @GetMapping("/book-upload")
    public String home() {
        return "book-upload";
    }

    @PostMapping("/book-upload")
    public String add(@Valid @ModelAttribute CreateBookRequest request, HttpServletResponse response, Model model) throws IOException {
        var create = bookService.create(request);
        return "book-upload";
    }
}
