package com.example.insurance.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PolicyResponse(
  Long id, Long clientId, String policyTypeCode,
  LocalDate startDate, LocalDate endDate, BigDecimal premium, String status
) {}
