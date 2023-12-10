package com.webJava.library.service;

import com.webJava.library.dto.book.CreateBookRequest;
import com.webJava.library.dto.book.GetBookResponse;
import com.webJava.library.dto.PageDto;

import java.io.IOException;
public interface BookService {
    int create(CreateBookRequest request) throws IOException;

    GetBookResponse getById(int id);

    PageDto<GetBookResponse> getAll(int pageNumber, int pageSize);

    byte[] getBookImage(int bookId);

    void delete(int id);
}
