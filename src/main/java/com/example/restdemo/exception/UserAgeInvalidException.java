package com.example.restdemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserAgeInvalidException extends RuntimeException{
  public UserAgeInvalidException(Integer age) {
    super("Invalid user age: " + age + ", Age should be < 150");
  }
}
