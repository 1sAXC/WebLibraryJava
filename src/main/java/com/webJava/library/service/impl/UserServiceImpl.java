package com.webJava.library.service.impl;

import com.webJava.library.dto.book.GetBookResponse;
import com.webJava.library.dto.user.UpdateUserRequest;
import com.webJava.library.models.Book;
import com.webJava.library.repository.BookRepository;
import io.micrometer.observation.annotation.Observed;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.webJava.library.dto.PageDto;
import com.webJava.library.dto.user.ChangeUserPasswordRequest;
import com.webJava.library.dto.user.GetUserResponse;
import com.webJava.library.exceptions.EntityNotFoundException;
import com.webJava.library.exceptions.ValidationException;
import com.webJava.library.helpers.ImageUtils;
import com.webJava.library.helpers.PageDtoMaker;
import com.webJava.library.models.Image;
import com.webJava.library.models.User;
import com.webJava.library.repository.ImageRepository;
import com.webJava.library.repository.RoleRepository;
import com.webJava.library.repository.UserRepository;
import com.webJava.library.service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final PageDtoMaker<User, GetUserResponse> pageUserDtoMaker;
    private final PageDtoMaker<Book, GetBookResponse> pageBookDtoMaker;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ImageUtils imageUtils;
    private final ImageRepository imageRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BookRepository bookRepository, PageDtoMaker<User, GetUserResponse> pageDtoMaker,
                           PageDtoMaker<Book, GetBookResponse> pageBookDtoMaker, PasswordEncoder passwordEncoder, RoleRepository roleRepository, ImageUtils imageUtils, ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.pageBookDtoMaker = pageBookDtoMaker;
        this.pageUserDtoMaker = pageDtoMaker;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.imageUtils = imageUtils;
        this.imageRepository = imageRepository;
    }
    @Override
    public GetUserResponse getById(int id) {
        var user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));

        return mapToResponse(user);
    }

    @Override
    @Transactional
    public byte[] getUserAvatar(int id) {
        var user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        var avatar = user.getAvatar();

        return imageUtils.decompress(avatar.getData());
    }

    @Override
    public boolean userHasBook(int userId, int bookId) {
        return bookRepository.existsByIdAndUsers_Id(bookId, userId);
    }

    @Override
    public Image getAvatar(int userId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return user.getAvatar();
    }

    @Override
    public PageDto<GetUserResponse> getAll(int pageNumber, int pageSize) {
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        var page = userRepository.findAll(pageRequest);

        return pageUserDtoMaker.makePageDto(page, this::mapToResponse);
    }

    @Override
    @Transactional
    public GetUserResponse update(int id, UpdateUserRequest request) throws IOException {
        var user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        var avatar = request.getImage();
        var image = new Image(avatar.getOriginalFilename(), avatar.getContentType(), imageUtils.compress(avatar.getBytes()));
        user.setRole(roleRepository.getById(request.getRole()));
        user.setAvatar(image);
        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        var updatedUser = userRepository.save(user);

        return mapToResponse(updatedUser);
    }

    @Override
    public GetUserResponse getUserByName(String username) {
        var user = userRepository.findAllByUsername(username);
        return mapToResponse(user.get());
    }


    @Override
    public void delete(int id) {
        var user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        userRepository.delete(user);
    }

    private GetUserResponse mapToResponse(User user) {
        return new GetUserResponse(user.getId(), user.getRole().getId(), user.getRole().getName(), user.getUsername(), user.getPassword(), user.getFirstName(),
                user.getLastName());
    }

    @Observed
    public ArrayList<String> UserNames()
    {
        ArrayList<String> usernames = new ArrayList<String>();
        List<User> users = userRepository.findAll();
        List<GetUserResponse> users2= users.stream().map(this::mapToResponse).toList();
        for (int i = 0; i < users2.size(); i++)
        {
            usernames.add(users2.get(i).getUsername());
        }
        return usernames;
    }
}
