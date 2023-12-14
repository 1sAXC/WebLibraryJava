package com.webJava.library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.security.SignatureException;
import java.util.Date;
import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorModel> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        var errorModel = new ErrorModel();
        errorModel.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorModel.setMessage(ex.getMessage());
        errorModel.setTimestamp(new Date());

        return new ResponseEntity<>(errorModel, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ErrorModel> handleEntityAlreadyExistsException(EntityAlreadyExistsException ex, WebRequest request) {
        var errorModel = new ErrorModel();
        errorModel.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorModel.setMessage(ex.getMessage());
        errorModel.setTimestamp(new Date());

        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorModel> handleValidationException(ValidationException ex, WebRequest request) {
        var errorModel = new ErrorModel();
        errorModel.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorModel.setMessage(ex.getMessage());
        errorModel.setTimestamp(new Date());

        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorModel> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        var errorModel = new ErrorModel();
        errorModel.setStatusCode(HttpStatus.FORBIDDEN.value());
        errorModel.setMessage(ex.getMessage());
        errorModel.setTimestamp(new Date());

        return new ResponseEntity<>(errorModel, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorModel> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        var errorModel = new ValidationErrorModel();
        errorModel.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorModel.setMessage("Data is not valid");
        errorModel.setTimestamp(new Date());

        var errors = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        errorModel.setErrors(errors);

        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }

    /*@ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла внутренняя ошибка сервера: " + ex.getMessage());
    }*/

}
