package com.example.insurance.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "policy_type")
public class PolicyType {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 50)
  private String code;

  @Column(nullable = false, length = 150)
  private String name;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal basePremium;
}
