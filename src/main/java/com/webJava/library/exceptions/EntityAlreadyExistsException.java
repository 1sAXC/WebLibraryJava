package com.webJava.library.exceptions;

import java.io.Serial;

public class EntityAlreadyExistsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 2;

    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
