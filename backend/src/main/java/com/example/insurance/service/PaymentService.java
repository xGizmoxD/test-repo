package com.example.insurance.service;

import com.example.insurance.domain.Payment;
import com.example.insurance.domain.Policy;
import com.example.insurance.dto.PaymentDtos.*;
import com.example.insurance.repository.PaymentRepository;
import com.example.insurance.repository.PolicyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PaymentService {

  private final PaymentRepository payRepo;
  private final PolicyRepository policyRepo;

  public PaymentService(PaymentRepository payRepo, PolicyRepository policyRepo) {
    this.payRepo = payRepo; this.policyRepo = policyRepo;
  }

  @Transactional
  public PaymentResponse add(Long policyId, CreatePaymentRequest req) {
    Policy policy = policyRepo.findById(policyId)
        .orElseThrow(() -> new NoSuchElementException("Policy not found: " + policyId));

    Payment p = Payment.builder()
        .policy(policy)
        .amount(req.amount())
        .method(req.method())
        .build();

    p = payRepo.save(p);
    return toResp(p);
  }

  @Transactional(readOnly = true)
  public List<PaymentResponse> list(Long policyId) {
    if (!policyRepo.existsById(policyId))
      throw new NoSuchElementException("Policy not found: " + policyId);

    return payRepo.findByPolicy_IdOrderByPaidAtDesc(policyId)
        .stream().map(this::toResp).toList();
  }

  private PaymentResponse toResp(Payment p) {
    return new PaymentResponse(p.getId(), p.getPolicy().getId(),
        p.getAmount(), p.getPaidAt(), p.getMethod());
  }
}
