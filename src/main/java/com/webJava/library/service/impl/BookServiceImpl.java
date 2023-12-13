package com.webJava.library.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.webJava.library.dto.PageDto;
import com.webJava.library.dto.book.CreateBookRequest;
import com.webJava.library.dto.book.GetBookResponse;
import com.webJava.library.exceptions.AccessDeniedException;
import com.webJava.library.exceptions.EntityNotFoundException;
import com.webJava.library.exceptions.ValidationException;
import com.webJava.library.helpers.AuthFacade;
import com.webJava.library.helpers.ImageUtils;
import com.webJava.library.helpers.PageDtoMaker;
import com.webJava.library.models.Image;
import com.webJava.library.models.Book;
import com.webJava.library.repository.ImageRepository;
import com.webJava.library.repository.BookRepository;
import com.webJava.library.repository.UserRepository;
import com.webJava.library.service.BookService;

import java.io.IOException;
import java.util.Base64;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthFacade authFacade;
    private final UserRepository userRepository;
    private final PageDtoMaker<Book, GetBookResponse> pageDtoMaker;
    private final ImageUtils imageUtils;
    private final ImageRepository imageRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthFacade authFacade, UserRepository userRepository, PageDtoMaker<Book, GetBookResponse> pageDtoMaker, ImageUtils imageUtils, ImageRepository imageRepository) {
        this.bookRepository = bookRepository;
        this.authFacade = authFacade;
        this.userRepository = userRepository;
        this.pageDtoMaker = pageDtoMaker;
        this.imageUtils = imageUtils;
        this.imageRepository = imageRepository;
    }

    @Override
    public int create(CreateBookRequest request) throws IOException {
        /*var username = authFacade.getAuth().getName();
        var user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (user.getRole().getName().equals("default"))
            throw new AccessDeniedException("Access Denied");*/
        var file = request.getImage();
        var image = new Image(file.getOriginalFilename(), file.getContentType(), imageUtils.compress(file.getBytes()));
        var post = new Book(image, request.getTitle(), request.getAuthor(), request.getAnnotation());

        var newPost = bookRepository.save(post);

        return newPost.getId();
    }

    @Override
    @Transactional
    public GetBookResponse getById(int id) {
        var book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));

        return mapToResponse(book);
    }

    @Override
    @Transactional
    public byte[] getBookImage(int postId) {
        var book = bookRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        var image = book.getImage();

        return imageUtils.decompress(image.getData());
    }


    @Override
    @Transactional
    public PageDto<GetBookResponse> getAll(int pageNumber, int pageSize) {
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        var page = bookRepository.findAll(pageRequest);

        return pageDtoMaker.makePageDto(page, this::mapToResponse);
    }


    @Override
    public void delete(int id) {
        var username = authFacade.getAuth().getName();
        var currentUser = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
        var book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));

        if (currentUser.getRole().getName().equals("default"))
            throw new AccessDeniedException("Access Denied");

        bookRepository.delete(book);
    }

    public GetBookResponse mapToResponse(Book book) {
        return new GetBookResponse(book.getId(), book.getTitle(), book.getAuthor(), book.getAnnotation());
    }
}

