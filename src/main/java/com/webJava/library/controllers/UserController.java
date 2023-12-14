package com.webJava.library.controllers;

import com.webJava.library.dto.book.CreateBookRequest;
import com.webJava.library.dto.book.GetBookResponse;
import com.webJava.library.dto.user.CreateUserRequest;
import com.webJava.library.dto.user.GetUserResponse;
import com.webJava.library.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/user-edit")
    public String add(Model model) throws IOException {
        List<GetUserResponse> userList = retrieveUserList();
        model.addAttribute("users", userList);
        return "user-edit";
    }
    @GetMapping(value = "/{id}/avatarImage", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getAvatarImage(@PathVariable int id) {
        try{
        var avatar = userService.getAvatar(id);
        if (avatar.getType().equals("application/octet-stream")){
            return ResponseEntity.ok(userService.getUserAvatar(1));
        }
        return ResponseEntity.ok(userService.getUserAvatar(id));}
        catch(Exception ex){
            return ResponseEntity.ok(userService.getUserAvatar(1));
        }
    }

    @PostMapping("/{id}/user-delete")
    public String deleteUser(HttpServletResponse response, Model model, @PathVariable int id) throws IOException {
        model.addAttribute("user", userService.getById(id));
        userService.delete(id);
        return "profile";
    }

    private List<GetUserResponse> retrieveUserList() {
        var users = this.userService.getAll(0, 10).getContent();
        return users;
    }
}
