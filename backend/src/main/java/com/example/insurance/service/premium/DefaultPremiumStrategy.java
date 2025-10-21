package com.example.insurance.service.premium;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;


@Component
public class DefaultPremiumStrategy implements PremiumStrategy {

    private final AgeBasedPremiumStrategyHelper ageHelper;
    
    public DefaultPremiumStrategy(AgeBasedPremiumStrategyHelper ageHelper) {
        this.ageHelper = ageHelper;
    }

    @Override
    public BigDecimal calculate(BigDecimal basePerMonth, LocalDate birth, LocalDate start, LocalDate end) {
        int months = Math.max(1, Period.between(start,end).getYears() * 12 + Period.between(start,end).getMonths()  
        );
        BigDecimal baseForPeriod = basePerMonth.multiply(BigDecimal.valueOf(months));
        int age = Period.between(birth, start).getYears();
        return ageHelper.apply(baseForPeriod, age).setScale(2, java.math.RoundingMode.HALF_UP);
    }
}
