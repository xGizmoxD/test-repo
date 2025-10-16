package com.example.insurance.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "policy")
public class Policy {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false) @JoinColumn(name = "client_id")
  private Client client;

  @ManyToOne(optional = false) @JoinColumn(name = "policy_type_id")
  private PolicyType policyType;

  @Column(nullable = false)
  private LocalDate startDate;

  @Column(nullable = false)
  private LocalDate endDate;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal premium;

  @Builder.Default
  @Column(nullable = false, length = 20)
  private String status = "ACTIVE";

  @Builder.Default
  @Column(nullable = false)
  private Instant createdAt = Instant.now();
}
