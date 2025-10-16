package com.example.insurance.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class PolicyDtos {

  // Wejście zgodne z Twoim PolicyService.create(...)
  public record CreatePolicyRequest(
      @NotNull Long clientId,
      @NotBlank @Size(max = 50) String policyTypeCode,
      @NotNull LocalDate startDate,
      @NotNull LocalDate endDate
  ) {}

  // Wyjście zgodne z tym, co zwracasz w serwisie
  public record PolicyResponse(
      Long id,
      Long clientId,
      String policyTypeCode,
      LocalDate startDate,
      LocalDate endDate,
      BigDecimal premium,
      String status
  ) {}
}