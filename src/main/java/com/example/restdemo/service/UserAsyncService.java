package com.example.restdemo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserAsyncService {

  @Async("userExecutor")
  public void logUserCreated(Long userId) {
    log.info("Async task started for user id = {}, thread={}",
        userId, Thread.currentThread().getName());

    try {
      Thread.sleep(2000); // simulation of post-creation work
    } catch (InterruptedException e) {
      log.error("Async task interrupted", e);
      Thread.currentThread().interrupt();
    }

    log.info("Async task finished for userId={}", userId);
  }
}

