package com.example.insurance.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "client")
public class Client {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String firstName;

  @Column(nullable = false, length = 100)
  private String lastName;

  @Column(nullable = false, unique = true, length = 255)
  private String email;

  @Column(nullable = false)
  private LocalDate birthDate;

  @Column(nullable = false)
  private Instant createdAt;

  // ⇩⇩⇩ FABRYKA wykorzystywana w ClientCommandService ⇩⇩⇩
  public static Client createFrom(String firstName, String lastName, String email, LocalDate birthDate) {
    Client c = new Client();
    c.firstName = firstName;
    c.lastName  = lastName;
    c.email     = email;
    c.birthDate = birthDate;
    c.createdAt = Instant.now();
    return c;
  }
}
