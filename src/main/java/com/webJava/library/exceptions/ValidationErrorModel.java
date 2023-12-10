package com.webJava.library.exceptions;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class ValidationErrorModel {
    private int statusCode;
    private String message;
    private Map<String, String> errors;
    private Date timestamp;
}