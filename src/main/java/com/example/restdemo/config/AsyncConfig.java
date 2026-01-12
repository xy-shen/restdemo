package com.example.restdemo.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AsyncConfig {

  @Bean(destroyMethod = "shutdown")
  public ExecutorService userExecutor() {
    return Executors.newFixedThreadPool(2); // small demo pool
  }
}
