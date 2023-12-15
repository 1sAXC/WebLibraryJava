package com.webJava.library.exceptions;

public class RegistEx extends RuntimeException {
    private String message;

    public RegistEx(String message1) {
        this.message = message1;
    }

    @Override
    public String getMessage() {
        return message;
    }
}