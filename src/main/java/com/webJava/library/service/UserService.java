package com.webJava.library.service;

import com.webJava.library.dto.user.UpdateUserRequest;
import org.springframework.web.multipart.MultipartFile;
import com.webJava.library.dto.PageDto;
import com.webJava.library.dto.user.ChangeUserPasswordRequest;
import com.webJava.library.dto.user.GetUserResponse;

import java.io.IOException;

public interface UserService {
    /**
     * Получает пользователя, отправившего запрос.
     *
     * @return пользователя
     */
    GetUserResponse getCurrentUser();

    /**
     * Получает аватар пользователя, отправившего запрос.
     *
     * @return аватар пользователя
     */
    byte[] getCurrentUserAvatar();

    /**
     * Получает пользователя по указанному идентификатору.
     *
     * @param id идентификатор пользователя
     * @return пользователя с указанным идентификатором
     */
    GetUserResponse getById(int id);

    /**
     * Получает аватар пользователя.
     *
     * @param id идентификатор пользователя
     * @return аватар пользователя
     */
    byte[] getUserAvatar(int id);

    /**
     * Получает всех пользователей.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество пользователей на странице
     * @return объект PageDto, содержащий пользователей
     */
    PageDto<GetUserResponse> getAll(int pageNumber, int pageSize);

    /**
     * Получает всех пользователей, отфильтрованных по идентификатору роли.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество пользователей на странице
     * @param roleId     идентификатор роли, которую должен иметь пользователь
     * @return объект PageDto, содержащий пользователей
     */
    PageDto<GetUserResponse> getAllByRoleId(int pageNumber, int pageSize, int roleId);

    /**
     * Получает всех пользователей, отфильтрованных по названию роли.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество пользователей на странице
     * @param name       название роли, которую должен иметь пользователь
     * @return объект PageDto, содержащий пользователей
     */
    PageDto<GetUserResponse> getAllByRoleName(int pageNumber, int pageSize, String name);

    /**
     * Меняет пароль пользователя.
     *
     * @param request объект запроса на смену пароля
     * @return пользователя
     */
    GetUserResponse changePassword(ChangeUserPasswordRequest request);

    /**
     * Меняет роль пользователя.
     *
     * @param id      идентификатор пользователя
     * @param request объект запроса на смену роли
     * @return обновленного пользователя
     */
    GetUserResponse changeRole(int id, UpdateUserRequest request);

    /**
     * Меняет аватар пользователя.
     *
     * @param avatar новый аватар пользователя
     * @return обновленного пользователя
     */
    GetUserResponse changeAvatar(MultipartFile avatar) throws IOException;

    /**
     * Удаляет пользователя по указанному идентификатору.
     *
     * @param id идентификатор пользователя
     */
    void delete(int id);
}
