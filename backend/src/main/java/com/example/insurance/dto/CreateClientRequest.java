package com.example.insurance.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record CreateClientRequest(
  @NotBlank @Size(max = 100) String firstName,
  @NotBlank @Size(max = 100) String lastName,
  @Email @NotBlank @Size(max = 255) String email,
  @NotNull LocalDate birthDate
) {}