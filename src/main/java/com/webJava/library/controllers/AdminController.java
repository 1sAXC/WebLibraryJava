package com.webJava.library.controllers;

import com.webJava.library.dto.auth.LoginRequest;
import com.webJava.library.dto.auth.RegisterRequest;
import com.webJava.library.dto.book.CreateBookRequest;
import com.webJava.library.dto.post.CreatePostRequest;
import com.webJava.library.dto.post.UpdatePostRequest;
import com.webJava.library.models.User;
import com.webJava.library.security.JwtGenerator;
import com.webJava.library.service.BookService;
import com.webJava.library.service.PostService;
import com.webJava.library.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class AdminController {

    private final BookService bookService;
    private final PostService postService;
    private final UserService userService;
    private final JwtGenerator jwt;
    public AdminController(BookService bookService, PostService postService, UserService userService, JwtGenerator jwt){
        this.bookService = bookService;
        this.postService = postService;
        this.userService = userService;
        this.jwt = jwt;
    }
    @GetMapping("/book-upload")
    public String home() {
        return "book-upload";
    }

    @GetMapping("/post-upload")
    public String home2(@CookieValue("AccessToken") String token) {
        var username = jwt.getUsernameFromJwt(token);
        var user = userService.getUserByName(username);
        if (user.getRoleId() <2)
        {
            return ("AccessDenied");
        }
        return "post-upload";
    }

    @PostMapping("/book-upload")
    public String add(@Valid @ModelAttribute CreateBookRequest request, HttpServletResponse response, Model model) throws IOException {
        var create = bookService.create(request);
        return "book-upload";
    }

    @PostMapping("/post-upload")
    public String addPost(@Valid @ModelAttribute CreatePostRequest request, HttpServletResponse response, Model model, @CookieValue("AccessToken") String token) throws IOException {
        var username = jwt.getUsernameFromJwt(token);
        var user = userService.getUserByName(username);
        if (user.getRoleId() <2)
        {
            return ("AccessDenied");
        }

        request.setUsername(username);
        var create = postService.create(request);
        return "post-upload";
    }

    @PutMapping("/{id}/post-update")
    public String updatePost(@Valid @ModelAttribute UpdatePostRequest request, HttpServletResponse response, Model model, @PathVariable int id) throws IOException {
        model.addAttribute("post", postService.getById(id));
        var update = postService.update(id, request);
        return "post-update";
    }

    @GetMapping("/{id}/post-update")
    public String getUpdatePost(HttpServletResponse response, Model model, @PathVariable int id, @CookieValue("AccessToken") String token) throws IOException {
        model.addAttribute("post", postService.getById(id));
        var username = jwt.getUsernameFromJwt(token);
        var user = userService.getUserByName(username);
        if (user.getRoleId() <2)
        {
            return ("AccessDenied");
        }
        return "post-update";
    }

    @PostMapping("/{id}/post-delete")
    public String deletePost(HttpServletResponse response, Model model, @PathVariable int id) throws IOException {
        model.addAttribute("post", postService.getById(id));
        postService.delete(id);
        return "about";
    }
}
