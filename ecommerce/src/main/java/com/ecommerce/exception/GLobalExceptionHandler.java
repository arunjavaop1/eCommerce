package com.ecommerce.exception;

import com.ecommerce.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;

@ControllerAdvice
public class GLobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException u) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                u.getMessage(),
                "/users"
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserFound(UserFoundException u) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.ALREADY_REPORTED.value(),
                u.getMessage(),
                "/users"
        );
        return new ResponseEntity<>(error, HttpStatus.ALREADY_REPORTED);
    }



}
