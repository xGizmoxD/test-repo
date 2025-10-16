package com.example.insurance;

import com.example.insurance.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "com.example.insurance")
@Import(SecurityConfig.class)
public class InsuranceApplication {
  public static void main(String[] args) {
    SpringApplication.run(InsuranceApplication.class, args);
  }
}