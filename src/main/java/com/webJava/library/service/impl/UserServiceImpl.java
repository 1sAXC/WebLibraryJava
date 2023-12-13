package com.webJava.library.service.impl;

import com.webJava.library.dto.book.GetBookResponse;
import com.webJava.library.dto.user.UpdateUserRequest;
import com.webJava.library.models.Book;
import com.webJava.library.repository.BookRepository;
import com.webJava.library.service.BookService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.webJava.library.dto.PageDto;
import com.webJava.library.dto.user.ChangeUserPasswordRequest;
import com.webJava.library.dto.user.GetUserResponse;
import com.webJava.library.exceptions.AccessDeniedException;
import com.webJava.library.exceptions.EntityNotFoundException;
import com.webJava.library.exceptions.ValidationException;
import com.webJava.library.helpers.AuthFacade;
import com.webJava.library.helpers.ImageUtils;
import com.webJava.library.helpers.PageDtoMaker;
import com.webJava.library.models.Image;
import com.webJava.library.models.User;
import com.webJava.library.repository.ImageRepository;
import com.webJava.library.repository.RoleRepository;
import com.webJava.library.repository.UserRepository;
import com.webJava.library.service.UserService;

import java.io.IOException;
import java.util.Base64;

/**
 * Класс сервиса пользователей.
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final PageDtoMaker<User, GetUserResponse> pageUserDtoMaker;
    private final PageDtoMaker<Book, GetBookResponse> pageBookDtoMaker;
    private final AuthFacade authFacade;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ImageUtils imageUtils;
    private final ImageRepository imageRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BookRepository bookRepository, PageDtoMaker<User, GetUserResponse> pageDtoMaker,
                           PageDtoMaker<Book, GetBookResponse> pageBookDtoMaker, AuthFacade authFacade, PasswordEncoder passwordEncoder, RoleRepository roleRepository, ImageUtils imageUtils, ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.pageBookDtoMaker = pageBookDtoMaker;
        this.pageUserDtoMaker = pageDtoMaker;
        this.authFacade = authFacade;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.imageUtils = imageUtils;
        this.imageRepository = imageRepository;
    }

    /**
     * Получает пользователя, отправившего запрос.
     *
     * @return пользователя
     */
    @Override
    public GetUserResponse getCurrentUser() {
        var username = authFacade.getAuth().getName();
        var user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));

        return mapToResponse(user);
    }

    /**
     * Получает аватар пользователя, отправившего запрос.
     *
     * @return аватар пользователя
     */
    @Override
    @Transactional
    public byte[] getCurrentUserAvatar() {
        var username = authFacade.getAuth().getName();
        var user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
        var avatar = user.getAvatar();

        return imageUtils.decompress(avatar.getData());
    }

    /**
     * Получает пользователя по указанному идентификатору.
     *
     * @param id идентификатор пользователя
     * @return пользователя с указанным идентификатором
     */
    @Override
    public GetUserResponse getById(int id) {
        var user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));

        return mapToResponse(user);
    }

    /**
     * Получает аватар пользователя.
     *
     * @param id идентификатор пользователя
     * @return аватар пользователя
     */
    @Override
    @Transactional
    public byte[] getUserAvatar(int id) {
        var user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        var avatar = user.getAvatar();

        return imageUtils.decompress(avatar.getData());
    }

    /**
     * Получает всех пользователей.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество пользователей на странице
     * @return объект PageDto, содержащий пользователей
     */
    @Override
    public PageDto<GetUserResponse> getAll(int pageNumber, int pageSize) {
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        var page = userRepository.findAll(pageRequest);

        return pageUserDtoMaker.makePageDto(page, this::mapToResponse);
    }

    /**
     * Получает всех пользователей, отфильтрованных по названию роли.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество пользователей на странице
     * @param name       название роли, которую должен иметь пользователь
     * @return объект PageDto, содержащий пользователей
     */
    @Override
    public PageDto<GetUserResponse> getAllByRoleName(int pageNumber, int pageSize, String name) {
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        var page = userRepository.findAllByRole_Name(name, pageRequest);

        return pageUserDtoMaker.makePageDto(page, this::mapToResponse);
    }

    /**
     * Меняет пароль пользователя.
     *
     * @param request объект запроса на смену пароля
     * @return пользователя
     */
    @Override
    public GetUserResponse changePassword(ChangeUserPasswordRequest request) {
        var username = authFacade.getAuth().getName();
        var user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword()))
            throw new ValidationException("Wrong password");

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword()))
            throw new ValidationException("Password must be new");

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        var updatedUser = userRepository.save(user);

        return mapToResponse(updatedUser);
    }


    /**
     * Меняет роль пользователя.
     *
     * @param id      идентификатор пользователя
     * @param request объект запроса на смену роли
     * @return обновленного пользователя
     */
    @Override
    public GetUserResponse changeRole(int id, UpdateUserRequest request) {
        var authorities = authFacade.getAuth().getAuthorities();

        if (!authorities.contains(new SimpleGrantedAuthority("admin")))
            throw new AccessDeniedException("Access denied");

        var user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        var role = roleRepository.findById(request.getRoleId()).orElseThrow(() -> new EntityNotFoundException("Role not found"));

        user.setRole(role);

        var updatedUser = userRepository.save(user);

        return mapToResponse(updatedUser);
    }

    /**
     * Меняет аватар пользователя.
     *
     * @param avatar новый аватар пользователя
     * @return обновленного пользователя
     */
    @Override
    @Transactional
    public GetUserResponse changeAvatar(MultipartFile avatar) throws IOException {
        var username = authFacade.getAuth().getName();
        var user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
        imageRepository.delete(user.getAvatar());

        var image = new Image(avatar.getOriginalFilename(), avatar.getContentType(), imageUtils.compress(avatar.getBytes()));
        user.setAvatar(image);

        var updatedUser = userRepository.save(user);

        return mapToResponse(updatedUser);
    }

    @Override
    public PageDto<GetBookResponse> getAllBooks(int pageNumber, int pageSize, int userId) {
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Book> page = bookRepository.findAllByUsers_Id(userId, pageRequest);

        return pageBookDtoMaker.makePageDto(page, this::mapBookToResponse);
    }

    /**
     * Удаляет пользователя по указанному идентификатору.
     *
     * @param id идентификатор пользователя
     */
    @Override
    public void delete(int id) {
        var authorities = authFacade.getAuth().getAuthorities();
        var username = authFacade.getAuth().getName();
        var currentUser = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!authorities.contains(new SimpleGrantedAuthority("ADMIN")) && currentUser.getId() != id)
            throw new AccessDeniedException("Access denied");

        var user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        userRepository.delete(user);
    }

    /**
     * Преобразует UserEntity в GetUserResponse.
     *
     * @param user объект UserEntity, который будет преобразован
     * @return объект GetUserResponse, содержащий данные из объекта UserEntity
     */
    private GetUserResponse mapToResponse(User user) {
        return new GetUserResponse(user.getId(), user.getRole().getId(), user.getUsername(), user.getFirstName(),
                user.getLastName(), user.getCreatedAt());
    }

    private GetBookResponse mapBookToResponse(Book book) {
        return new GetBookResponse(book.getId(), book.getTitle(), book.getAuthor(), book.getAnnotation());
    }
}
