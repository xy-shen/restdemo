package com.example.restdemo.service;

import com.example.restdemo.entity.User;
import com.example.restdemo.exception.UserAgeInvalidException;
import com.example.restdemo.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  private final UserRepository repo;

  @Autowired
  public UserService(UserRepository repo) {
    this.repo = repo;
  }

  @Transactional(rollbackFor = Exception.class)
  public User create(User user) {
    if (user.getAge() > 150) throw new UserAgeInvalidException(user.getAge());
    return repo.save(user);
  }

  @Transactional(readOnly = true)
  public List<User> getAll() {
    return repo.findAll();
  }

  @Transactional(readOnly = true)
  public User getById(Long id) {
    return repo.findById(id).orElse(null);
  }

  @Transactional(rollbackFor = Exception.class)
  public User update(Long id, User req) {
    User existing = getById(id);
    existing.setName(req.getName());
    existing.setAge(req.getAge());
    existing.setSalary(req.getSalary());
    return repo.save(existing);
  }

  @Transactional(rollbackFor = Exception.class)
  public void delete(Long id) {
    if (!repo.existsById(id)) {
      throw new RuntimeException("user not found with id: " + id);
    }
    repo.deleteById(id);
  }

}
