package com.webJava.library.exceptions;

import java.io.Serial;

public class AccessDeniedException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 4;

    public AccessDeniedException(String message) {
        super(message);
    }
}
