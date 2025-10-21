package com.example.insurance.service.premium;

import java.math.BigDecimal;
import java.time.LocalDate;


public interface PremiumStrategy {

     BigDecimal calculate(BigDecimal base, LocalDate birth, LocalDate start, LocalDate end);
     
    
}
