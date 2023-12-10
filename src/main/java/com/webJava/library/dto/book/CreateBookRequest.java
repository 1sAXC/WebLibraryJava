package com.webJava.library.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class CreateBookRequest {
    @Size(min = 3, max = 100)
    private String title;

    @Size(min = 3, max = 100)
    private String author;

    @NotBlank
    private String content;

    @NotBlank
    private String annotation;

    @NotBlank
    private MultipartFile image;
}
