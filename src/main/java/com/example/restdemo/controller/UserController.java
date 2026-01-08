package com.example.restdemo.controller;

import com.example.restdemo.entity.User;
import com.example.restdemo.service.UserService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
//  User user1 = new User(1L, "bob", 20, new BigDecimal(100));
//  User user2 = new User(2L, "alex", 30, new BigDecimal(200));
//  List<User> users = new ArrayList<>(Arrays.asList(user1, user2));

  private final UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @GetMapping("/test")
  public String test() {
    return "success";
  }

  @GetMapping
  public List<User> getAll() {
//    return users;
    return service.getAll();
  }

  @GetMapping("/{id}")
  public User getUserById(@PathVariable Long id) {
//    return user1;
    return service.getById(id);
  }

  @PostMapping("/{id}")
  public User update(@PathVariable Long id, @RequestBody User user) {
    return service.update(id, user);
  }

  @PostMapping
  public User create(@RequestBody User user) {
    return service.create(user);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }
}
