package com.webJava.library.service;

import com.webJava.library.dto.book.GetBookResponse;
import com.webJava.library.dto.post.GetPostResponse;
import com.webJava.library.dto.post.UpdatePostRequest;
import com.webJava.library.dto.user.UpdateUserRequest;
import com.webJava.library.models.Image;
import org.springframework.web.multipart.MultipartFile;
import com.webJava.library.dto.PageDto;
import com.webJava.library.dto.user.ChangeUserPasswordRequest;
import com.webJava.library.dto.user.GetUserResponse;

import java.io.IOException;

public interface UserService {

    GetUserResponse getById(int id);

    byte[] getUserAvatar(int id);


    PageDto<GetUserResponse> getAll(int pageNumber, int pageSize);
    GetUserResponse update(int id, UpdateUserRequest request) throws IOException;

    PageDto<GetUserResponse> getAllByRoleName(int pageNumber, int pageSize, String name);

    GetUserResponse changePassword(ChangeUserPasswordRequest request);

    GetUserResponse changeAvatar(MultipartFile avatar) throws IOException;

    PageDto<GetBookResponse> getAllBooks(int pageNumber, int pageSize, int userId);

    GetUserResponse getUserByName(String username);

    Image getAvatar(int userId);

    void AddBook(int userId, int bookId);
    void delete(int id);
}
