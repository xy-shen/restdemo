package com.example.restdemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserAgeInvalidException extends RuntimeException{
  public UserAgeInvalidException(Integer age) {
    super("Invalid user age: " + age + ", Age should be >= 0");
  }
}
