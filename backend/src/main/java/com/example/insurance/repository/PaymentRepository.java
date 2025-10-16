package com.example.insurance.repository;

import com.example.insurance.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
  List<Payment> findByPolicy_IdOrderByPaidAtDesc(Long policyId);
}
