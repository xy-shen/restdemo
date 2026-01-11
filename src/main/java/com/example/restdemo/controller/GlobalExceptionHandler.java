package com.example.restdemo.controller;

import com.example.restdemo.exception.ApiError;
import com.example.restdemo.exception.UserAgeInvalidException;
import com.example.restdemo.exception.UserNotFoundException;
import jakarta.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ApiError> exceptionHandlerUserNotFound(Exception ex) {
    ApiError error = new ApiError(
        Instant.now(),
        HttpStatus.NOT_FOUND.value(),
        ex.getMessage()
    );
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UserAgeInvalidException.class)
  public ResponseEntity<ApiError> exceptionHandlerUserAgeInvalid(Exception ex) {
    ApiError error = new ApiError(
        Instant.now(),
        HttpStatus.BAD_REQUEST.value(),
        ex.getMessage()
    );
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleBodyValidation(MethodArgumentNotValidException ex) {
    String msg = ex.getBindingResult().getFieldErrors().stream()
        .map(e -> e.getField() + ": " + e.getDefaultMessage())
        .collect(Collectors.joining(", "));

    ApiError error = new ApiError(Instant.now(), HttpStatus.BAD_REQUEST.value(), msg);
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ApiError> handleParamValidation(ConstraintViolationException ex) {
    String msg = ex.getConstraintViolations().stream()
        .map(v -> v.getPropertyPath() + ": " + v.getMessage())
        .collect(Collectors.joining(", "));

    ApiError error = new ApiError(Instant.now(), HttpStatus.BAD_REQUEST.value(), msg);
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }
}
