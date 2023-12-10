package com.webJava.library.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreatePostRequest {

    @Size(min = 3, max = 100)
    private String title;

    @NotBlank
    private String content;

    private MultipartFile image;
}
