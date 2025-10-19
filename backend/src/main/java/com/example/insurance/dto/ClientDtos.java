package com.example.insurance.dto;

import java.time.Instant;
import java.time.LocalDate;
import jakarta.validation.constraints.*;

public class ClientDtos {
  public record CreateClientRequest(
      @NotBlank String firstName,
      @NotBlank String lastName,
      @Email @NotBlank String email,
      @NotNull LocalDate birthDate
  ) {}

  public record ClientResponse(
      Long id,
      String firstName,
      String lastName,
      String email,
      LocalDate birthDate,
      Instant createdAt
  ) {}
}
