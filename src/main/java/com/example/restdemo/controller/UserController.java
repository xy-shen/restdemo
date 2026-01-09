package com.example.restdemo.controller;

import com.example.restdemo.entity.User;
import com.example.restdemo.exception.ApiError;
import com.example.restdemo.exception.UserNotFoundException;
import com.example.restdemo.service.UserService;
import jakarta.validation.Valid;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @GetMapping("/test")
  public String test() {
    return "success";
  }

  @GetMapping
  public List<User> getAll(@RequestParam(required = false, defaultValue = "salary") String sort) {
    List<User> users = service.getAll();
    if (sort.equals("salary")) {
      Collections.sort(users, (v1, v2) -> Double.compare(v1.getSalary(), v2.getSalary()));
    } else if (sort.equals("age")) {
      Collections.sort(users, (v1, v2) -> Integer.compare(v1.getAge(), v2.getAge()));
    }
    return users;
  }

  @GetMapping("/{id}")
  public User getUserById(@PathVariable Long id) {
//    return user1;
    User user = service.getById(id);
    if (user == null) {
      throw new UserNotFoundException(id);
    }
    return user;
  }

  @PostMapping("/{id}")
  public User update(@PathVariable Long id, @RequestBody User user) {
    return service.update(id, user);
  }

  @PostMapping
//  @ResponseStatus(value = HttpStatus.CREATED)
  public ResponseEntity<User> create(@Valid @RequestBody User user) {
    User created = service.create(user);
    return new ResponseEntity<>(created, HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ApiError> exceptionHandlerUserNotFound(Exception ex) {
    ApiError error = new ApiError(
        Instant.now(),
        HttpStatus.NOT_FOUND.value(),
        ex.getMessage()
    );
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }
}
