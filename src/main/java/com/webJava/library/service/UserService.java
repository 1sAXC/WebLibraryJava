package com.webJava.library.service;

import com.webJava.library.dto.book.GetBookResponse;
import com.webJava.library.dto.user.UpdateUserRequest;
import org.springframework.web.multipart.MultipartFile;
import com.webJava.library.dto.PageDto;
import com.webJava.library.dto.user.ChangeUserPasswordRequest;
import com.webJava.library.dto.user.GetUserResponse;

import java.io.IOException;

public interface UserService {
    GetUserResponse getCurrentUser();

    byte[] getCurrentUserAvatar();

    GetUserResponse getById(int id);

    byte[] getUserAvatar(int id);


    PageDto<GetUserResponse> getAll(int pageNumber, int pageSize);

    PageDto<GetUserResponse> getAllByRoleName(int pageNumber, int pageSize, String name);

    GetUserResponse changePassword(ChangeUserPasswordRequest request);

    GetUserResponse changeRole(int id, UpdateUserRequest request);

    GetUserResponse changeAvatar(MultipartFile avatar) throws IOException;

    PageDto<GetBookResponse> getAllBooks(int pageNumber, int pageSize, int userId);

    GetUserResponse getUserByName(String username);

    void count(int userId);
    void AddBook(int userId, int bookId);
    void delete(int id);
}
