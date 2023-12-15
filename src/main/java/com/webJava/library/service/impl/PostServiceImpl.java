package com.webJava.library.service.impl;

import com.webJava.library.models.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.webJava.library.dto.PageDto;
import com.webJava.library.dto.post.CreatePostRequest;
import com.webJava.library.dto.post.GetPostResponse;
import com.webJava.library.dto.post.UpdatePostRequest;
import com.webJava.library.exceptions.AccessDeniedException;
import com.webJava.library.exceptions.EntityNotFoundException;
import com.webJava.library.exceptions.ValidationException;
import com.webJava.library.helpers.ImageUtils;
import com.webJava.library.helpers.PageDtoMaker;
import com.webJava.library.models.Image;
import com.webJava.library.models.Post;
import com.webJava.library.repository.ImageRepository;
import com.webJava.library.repository.PostRepository;
import com.webJava.library.repository.UserRepository;
import com.webJava.library.service.PostService;

import java.io.IOException;
import java.util.Date;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PageDtoMaker<Post, GetPostResponse> pageDtoMaker;
    private final ImageUtils imageUtils;
    private final ImageRepository imageRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, PageDtoMaker<Post, GetPostResponse> pageDtoMaker, ImageUtils imageUtils, ImageRepository imageRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.pageDtoMaker = pageDtoMaker;
        this.imageUtils = imageUtils;
        this.imageRepository = imageRepository;
    }

    @Override
    public int create(CreatePostRequest request) throws IOException {
        /*var username = authFacade.getAuth().getName();
        var user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (user.getRole().getName().equals("default"))
            throw new AccessDeniedException("Access Denied");*/
        var file = request.getImage();
        var image = new Image(file.getOriginalFilename(), file.getContentType(), imageUtils.compress(file.getBytes()));
        var user = userRepository.findAllByUsername(request.getUsername());
        var post = new Post(user.get(), image, request.getTitle(), request.getContent());
        var newPost = postRepository.save(post);

        return newPost.getId();
    }

    @Override
    @Transactional
    public GetPostResponse getById(int id) {
        var post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));

        return mapToResponse(post);
    }

    @Override
    @Transactional
    public byte[] getPostImage(int postId) {
        var post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        var image = post.getImage();

        return imageUtils.decompress(image.getData());
    }


    @Override
    @Transactional
    public PageDto<GetPostResponse> getAll(int pageNumber, int pageSize) {
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        var page = postRepository.findAll(pageRequest);

        return pageDtoMaker.makePageDto(page, this::mapToResponse);
    }


    @Override
    @Transactional
    public GetPostResponse update(int id, UpdatePostRequest request) {
        var post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());

        var updatedPost = postRepository.save(post);

        return mapToResponse(updatedPost);
    }

    @Override
    public void delete(int id) {
        var post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        postRepository.delete(post);
    }
    
    private GetPostResponse mapToResponse(Post post) {
        return new GetPostResponse(post.getId(), post.getUser().getId(), post.getUser().getUsername(), post.getTitle(), post.getContent(),
                post.isPublished(), post.getCreatedAt(), post.getUpdatedAt());
    }
}
