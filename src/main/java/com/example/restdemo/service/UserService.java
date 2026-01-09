package com.example.restdemo.service;

import com.example.restdemo.entity.User;
import com.example.restdemo.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository repo;

  @Autowired
  public UserService(UserRepository repo) {
    this.repo = repo;
  }

  public User create(User user) {
    return repo.save(user);
  }

  public List<User> getAll() {
    return repo.findAll();
  }

  public User getById(Long id) {
    return repo.findById(id).orElse(null);
  }

  public User update(Long id, User req) {
    User existing = getById(id);
    existing.setName(req.getName());
    existing.setAge(req.getAge());
    existing.setSalary(req.getSalary());
    return repo.save(existing);
  }

  public void delete(Long id) {
    if (!repo.existsById(id)) {
      throw new RuntimeException("user not found with id: " + id);
    }
    repo.deleteById(id);
  }

}
