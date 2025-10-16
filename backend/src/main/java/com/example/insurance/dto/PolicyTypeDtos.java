package com.example.insurance.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class PolicyTypeDtos {

  public record CreatePolicyTypeRequest(
      @NotBlank @Size(max = 50) String code,
      @NotBlank @Size(max = 150) String name,
      @NotNull @DecimalMin("0.01") BigDecimal basePremium
  ) {}

  public record PolicyTypeResponse(
      Long id, String code, String name, BigDecimal basePremium
  ) {}
}
