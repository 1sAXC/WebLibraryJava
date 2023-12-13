package com.webJava.library.controllers;

import com.webJava.library.dto.book.GetBookResponse;
import com.webJava.library.models.Book;
import com.webJava.library.service.AuthService;
import com.webJava.library.service.BookService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
public class CatalogController {

    private final BookService bookService;

    public CatalogController(BookService bookService){
        this.bookService = bookService;
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
    public String getBook(@PathVariable int id, Model model) {
        model.addAttribute("book", this.bookService.getById(id));
        return "book";
    }

    private List<GetBookResponse> retrieveBookList() {
        var books = this.bookService.getAll(0, 10).getContent();
        return books;
    }
}
