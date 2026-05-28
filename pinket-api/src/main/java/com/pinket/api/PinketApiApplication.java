package com.pinket.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class PinketApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(PinketApiApplication.class, args);
  }
}
