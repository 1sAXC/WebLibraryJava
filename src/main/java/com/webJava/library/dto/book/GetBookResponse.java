package com.webJava.library.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
@Data
@AllArgsConstructor
public class GetBookResponse {
    private int id;

    private String title;

    private String author;

    private String annotation;

}
