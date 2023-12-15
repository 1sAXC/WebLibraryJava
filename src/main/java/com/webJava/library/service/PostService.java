package com.webJava.library.service;


import com.webJava.library.dto.post.CreatePostRequest;
import com.webJava.library.dto.post.GetPostResponse;
import com.webJava.library.dto.PageDto;
import com.webJava.library.dto.post.UpdatePostRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
public interface PostService {
    int create(CreatePostRequest request) throws IOException;

    GetPostResponse getById(int id);

    PageDto<GetPostResponse> getAll(int pageNumber, int pageSize);

    byte[] getPostImage(int postId);

    GetPostResponse update(int id, UpdatePostRequest request);

    void delete(int id);
}