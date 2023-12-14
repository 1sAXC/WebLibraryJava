package com.webJava.library.service;

import com.webJava.library.dto.book.CreateBookRequest;
import com.webJava.library.dto.book.GetBookResponse;
import com.webJava.library.dto.PageDto;
import com.webJava.library.dto.user.GetUserResponse;
import com.webJava.library.models.Book;
import com.webJava.library.models.User;

import java.io.IOException;
public interface BookService {
    int create(CreateBookRequest request) throws IOException;

    GetBookResponse getById(int id);

    PageDto<GetBookResponse> getAll(int pageNumber, int pageSize);

    byte[] getBookImage(int bookId);

    PageDto<GetBookResponse> getAllByUserId(int pageNumber, int pageSize, int userId);
    GetBookResponse mapToResponse(Book book);

    void addUser(int userId, int bookId);

    void delete(int id);

 /*   PageDto<GetUserResponse> getAllUser(int pageNumber, int pageSize, int bookId);*/
}
