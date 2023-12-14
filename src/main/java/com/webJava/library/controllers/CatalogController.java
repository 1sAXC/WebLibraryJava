package com.webJava.library.controllers;

import com.webJava.library.dto.book.GetBookResponse;
import com.webJava.library.models.Book;
import com.webJava.library.security.JwtGenerator;
import com.webJava.library.service.AuthService;
import com.webJava.library.service.BookService;
import com.webJava.library.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
public class CatalogController {

    private final BookService bookService;
    private final UserService userService;
    private final JwtGenerator jwt;

    public CatalogController(BookService bookService, UserService userService, JwtGenerator jwt){

        this.bookService = bookService;
        this.userService = userService;
        this.jwt = jwt;
    }
    @GetMapping("/catalog")
    public String catalog(Model model) {
        model.addAttribute("title", "Каталог книг");
        List<GetBookResponse> bookList = retrieveBookList();
        model.addAttribute("books", bookList);
        return "catalog";
    }

    @GetMapping(value = "/{id}/image", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getBookImage(@PathVariable int id) {
        return ResponseEntity.ok(bookService.getBookImage(id));
    }

    @GetMapping(value = "/book/{id}")
    public String getBook(@PathVariable int id, Model model, @CookieValue("AccessToken") String token) {
        var username = jwt.getUsernameFromJwt(token);
        var user = userService.getUserByName(username);
        model.addAttribute("book", this.bookService.getById(id));
        model.addAttribute("bookService", this.bookService);
        model.addAttribute("user", user);
        /*var users = bookService.getAllUser(0,100, id);*/
        return "book";
    }

    private List<GetBookResponse> retrieveBookList() {
        var books = this.bookService.getAll(0, 10).getContent();
        return books;
    }

    @GetMapping("/favoriteBooks")
    public String favoriteBooks(Model model, @CookieValue("AccessToken") String token) {
        var username = jwt.getUsernameFromJwt(token);
        var user = userService.getUserByName(username);
        model.addAttribute("title", "Избранное");
        List<GetBookResponse> bookList = retrieveFavoriteBookList(user.getId());
        model.addAttribute("books", bookList);
        return "favoriteBooks";
    }

    @Transactional
    private List<GetBookResponse> retrieveFavoriteBookList(int id) {
        var books = this.bookService.getAllByUserId(0,10,id).getContent();
        return books;
    }

    @PostMapping("book/addFavoriteBook/{id}")
    public String addFavoriteBook(Model model, @CookieValue("AccessToken") String token, @PathVariable int id) {
        var username = jwt.getUsernameFromJwt(token);
        var user = userService.getUserByName(username);
        model.addAttribute("title", "Избранное");
        List<GetBookResponse> bookList = retrieveFavoriteBookList(user.getId());
        model.addAttribute("books", bookList);
        bookService.addUser(user.getId(),id);
        return "catalog";
    }
}
