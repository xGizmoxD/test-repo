package com.example.insurance.service.premium;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AgeBasedPremiumStrategy implements PremiumStrategy {

  @Override
  public BigDecimal apply(BigDecimal base, int age) {
    BigDecimal result = base;
    if (age < 26) {
      result = result.multiply(BigDecimal.valueOf(1.20)); // +20%
    } else if (age > 60) {
      result = result.multiply(BigDecimal.valueOf(1.15)); // +15%
    }
    return result;
  }
}
