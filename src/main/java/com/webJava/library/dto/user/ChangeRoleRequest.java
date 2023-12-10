package com.webJava.library.dto.user;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ChangeRoleRequest {
    /**
     * Идентификатор роли.
     * Не может быть менее 1.
     */
    @Min(1)
    private int roleId;
}
