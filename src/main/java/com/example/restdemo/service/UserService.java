package com.example.restdemo.service;

import com.example.restdemo.entity.User;
import com.example.restdemo.exception.UserAgeInvalidException;
import com.example.restdemo.repository.UserRepository;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
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
  @Caching(
      put = @CachePut(cacheNames = "usersById", key = "#result.id"),
      evict = @CacheEvict(cacheNames = "usersAll", allEntries = true)
  )
  public User create(User user) {
    if (user.getAge() > 150) throw new UserAgeInvalidException(user.getAge());
    return repo.save(user);
  }

  @Transactional(readOnly = true)
  public List<User> getAll(String sort) {
    List<User> users = repo.findAll();
    if (sort.equals("salary")) {
      Collections.sort(users, (v1, v2) -> Double.compare(v1.getSalary(), v2.getSalary()));
    } else if (sort.equals("age")) {
      Collections.sort(users, (v1, v2) -> Integer.compare(v1.getAge(), v2.getAge()));
    }
    return users;
  }

  @Transactional(readOnly = true)
  public User getById(Long id) {
    return repo.findById(id).orElse(null);
  }

  @Transactional(rollbackFor = Exception.class)
  @Caching(
      put = @CachePut(cacheNames = "usersById", key = "#id"),
      evict = @CacheEvict(cacheNames = "usersAll", allEntries = true)
  )
  public User update(Long id, User req) {
    User existing = getById(id);
    existing.setName(req.getName());
    existing.setAge(req.getAge());
    existing.setSalary(req.getSalary());
    return repo.save(existing);
  }

  @Transactional(rollbackFor = Exception.class)
  @Caching(
      evict = {
          @CacheEvict(cacheNames = "usersById", key = "#id"),
          @CacheEvict(cacheNames = "usersAll", allEntries = true)
      }
  )
  public void delete(Long id) {
    if (!repo.existsById(id)) {
      throw new RuntimeException("user not found with id: " + id);
    }
    repo.deleteById(id);
  }

}
