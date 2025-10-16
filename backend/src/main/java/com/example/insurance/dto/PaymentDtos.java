package com.example.insurance.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;

public class PaymentDtos {

  public record CreatePaymentRequest(
      @NotNull @Positive BigDecimal amount,
      @NotBlank String method
  ) {}

  public record PaymentResponse(
      Long id, Long policyId, BigDecimal amount, Instant paidAt, String method
  ) {}
}
