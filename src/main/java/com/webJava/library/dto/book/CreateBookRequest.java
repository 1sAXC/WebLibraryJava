package com.webJava.library.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateBookRequest {
    @Size(min = 3, max = 100)
    private String title;

    @Size(min = 3, max = 100)
    private String author;

    @NotBlank
    private String annotation;

    private MultipartFile image;
}
