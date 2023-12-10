package com.webJava.library.exceptions;

import java.io.Serial;

public class ValidationException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 3;

    public ValidationException(String message) {
        super(message);
    }
}
