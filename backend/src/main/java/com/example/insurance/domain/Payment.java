package com.example.insurance.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "payment")
public class Payment {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false) @JoinColumn(name = "policy_id")
  private Policy policy;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal amount;

  @Builder.Default
  @Column(nullable = false)
  private Instant paidAt = Instant.now();

  @Column(nullable = false, length = 50)
  private String method; // np. CARD, TRANSFER, CASH
}
