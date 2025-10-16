package com.example.insurance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(auth -> auth
        // Swagger / OpenAPI – publicznie
        .requestMatchers(
          "/v3/api-docs",
          "/v3/api-docs/**",
          "/swagger-ui.html",
          "/swagger-ui/**",
          "/swagger-resources/**",
          "/webjars/**",
          "/favicon.ico"
        ).permitAll()
        
        //.requestMatchers("/api/hello").permitAll()
        .requestMatchers("/api/policies/*/payments/**").permitAll()
        
        .anyRequest().authenticated()
      )
      .httpBasic(Customizer.withDefaults());

    return http.build();
  }
}

