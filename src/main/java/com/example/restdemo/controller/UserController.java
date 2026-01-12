package com.example.restdemo.controller;

import com.example.restdemo.entity.User;
import com.example.restdemo.exception.UserNotFoundException;
import com.example.restdemo.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Slf4j
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
  @Cacheable(cacheNames = "usersAll", key = "#sort")
  public List<User> getAllSorted(@RequestParam(required = false, defaultValue = "salary") String sort) {
    return service.getAll(sort);
  }

  @GetMapping("/{id}")
  @Cacheable(cacheNames = "usersById", key = "#id")
  public User getUserById(@PathVariable @Min(1) Long id) {
//    return user1;
    User user = service.getById(id);
    if (user == null) {
      log.warn("Can't find user with id: {}", id);
      throw new UserNotFoundException(id);
    }
    return user;
  }

  @PostMapping("/{id}")
  public User update(@PathVariable @Min(1) Long id, @Valid @RequestBody User user) {
    return service.update(id, user);
  }

  @PostMapping
//  @ResponseStatus(value = HttpStatus.CREATED)
  public ResponseEntity<User> create(@Valid @RequestBody User user) {
    User created = service.create(user);
    return new ResponseEntity<>(created, HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable @Min(1) Long id) {
    service.delete(id);
  }

//  @ExceptionHandler(UserNotFoundException.class)
//  public ResponseEntity<ApiError> exceptionHandlerUserNotFound(Exception ex) {
//    ApiError error = new ApiError(
//        Instant.now(),
//        HttpStatus.NOT_FOUND.value(),
//        ex.getMessage()
//    );
//    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
//  }
}
