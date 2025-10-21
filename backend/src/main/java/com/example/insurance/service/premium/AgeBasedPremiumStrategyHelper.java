package com.example.insurance.service.premium;


import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class AgeBasedPremiumStrategyHelper {
    public BigDecimal apply(BigDecimal amount,int age){
        if (age <26) return amount.multiply(BigDecimal.valueOf(1.20));
        if (age >60) return amount.multiply(BigDecimal.valueOf(1.15));
        return amount;
    }
    
}
