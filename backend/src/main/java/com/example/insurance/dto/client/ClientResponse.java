package com.example.insurance.dto.client;

import java.time.LocalDate;
import java.time.Instant;

public record ClientResponse(
  Long id,
  String firstName,
  String lastName,
  String email,
  LocalDate birthDate,
  Instant createdAt
) {}