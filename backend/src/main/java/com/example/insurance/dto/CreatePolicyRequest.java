package com.example.insurance.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record CreatePolicyRequest(
  @NotNull Long clientId,
  @NotBlank String policyTypeCode,
  @NotNull LocalDate startDate,
  @NotNull LocalDate endDate
) {}
