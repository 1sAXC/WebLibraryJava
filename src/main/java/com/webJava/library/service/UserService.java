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
import java.util.ArrayList;
import java.util.List;

public interface UserService {

    GetUserResponse getById(int id);

    byte[] getUserAvatar(int id);

    boolean userHasBook(int userId, int bookId);

    PageDto<GetUserResponse> getAll(int pageNumber, int pageSize);
    GetUserResponse update(int id, UpdateUserRequest request) throws IOException;

    GetUserResponse getUserByName(String username);

    Image getAvatar(int userId);
    void delete(int id);

  ArrayList<String> UserNames();
}
