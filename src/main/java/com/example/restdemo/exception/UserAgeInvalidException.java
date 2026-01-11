package com.example.restdemo.exception;


public class UserAgeInvalidException extends RuntimeException{
  public UserAgeInvalidException(Integer age) {
    super("Invalid user age: " + age + ", Age should be < 150");
  }
}
