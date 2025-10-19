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

  @Builder.Default
  @Column(nullable = false)
  private Instant createdAt = Instant.now();

  /** Fabryka – bez setterów na zewnątrz */
  public static Client createFrom(String firstName,
                                  String lastName,
                                  String email,
                                  LocalDate birthDate) {
    return Client.builder()
        .firstName(firstName)
        .lastName(lastName)
        .email(email)
        .birthDate(birthDate)
        .createdAt(Instant.now())
        .build();
  }
}
