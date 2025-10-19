package com.example.insurance.service.premium;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Component
public class SimplePremiumStrategy implements PremiumStrategy {

  @Override
  public BigDecimal calculate(BigDecimal base, LocalDate birth, LocalDate start, LocalDate end) {
    // tyle miesiecy (min 1)
    int months = Math.max(1,
        Period.between(start, end).getMonths() + Period.between(start, end).getYears() * 12);

    BigDecimal prem = base.multiply(BigDecimal.valueOf(months));

    int age = Period.between(birth, start).getYears();
    if (age < 26) {
      prem = prem.multiply(BigDecimal.valueOf(1.20));  // +20%
    } else if (age > 60) {
      prem = prem.multiply(BigDecimal.valueOf(1.15));  // +15%
    }
    return prem.setScale(2, java.math.RoundingMode.HALF_UP);
  }
}
