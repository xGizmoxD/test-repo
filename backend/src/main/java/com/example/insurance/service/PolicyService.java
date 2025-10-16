package com.example.insurance.service;

import com.example.insurance.domain.*;
import com.example.insurance.dto.*;
import com.example.insurance.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.NoSuchElementException;

@Service
public class PolicyService {
  private final PolicyRepository policyRepo;
  private final PolicyTypeRepository typeRepo;
  private final ClientRepository clientRepo;

  public PolicyService(PolicyRepository p, PolicyTypeRepository t, ClientRepository c) {
    this.policyRepo = p; this.typeRepo = t; this.clientRepo = c;
  }

  @Transactional
  public PolicyResponse create(CreatePolicyRequest r){
    Client client = clientRepo.findById(r.clientId())
        .orElseThrow(() -> new IllegalArgumentException("Client not found"));

    PolicyType type = typeRepo.findByCode(r.policyTypeCode())
        .orElseThrow(() -> new IllegalArgumentException("PolicyType not found"));

    if (r.endDate().isBefore(r.startDate())) {
      throw new IllegalArgumentException("endDate must be after startDate");
    }

    BigDecimal premium = calculatePremium(
        type.getBasePremium(), client.getBirthDate(), r.startDate(), r.endDate()
    );

    Policy p = new Policy();
    p.setClient(client);
    p.setPolicyType(type);
    p.setStartDate(r.startDate());
    p.setEndDate(r.endDate());
    p.setPremium(premium);
    p.setStatus("ACTIVE");

    p = policyRepo.save(p);
    return new PolicyResponse(
        p.getId(),
        client.getId(),
        type.getCode(),
        p.getStartDate(),
        p.getEndDate(),
        p.getPremium(),
        p.getStatus()
    );
  }

  @Transactional(readOnly = true)
  public Page<PolicyResponse> list(Long clientId, Pageable pageable) {
    var page = (clientId == null)
        ? policyRepo.findAll(pageable)
        : policyRepo.findByClient_Id(clientId, pageable);

    return page.map(p -> new PolicyResponse(
        p.getId(),
        p.getClient().getId(),
        p.getPolicyType().getCode(),
        p.getStartDate(),
        p.getEndDate(),
        p.getPremium(),
        p.getStatus()
    ));
  }

  @Transactional(readOnly = true)
  public PolicyResponse get(Long id){
    Policy p = policyRepo.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Policy not found"));

    return new PolicyResponse(
        p.getId(),
        p.getClient().getId(),
        p.getPolicyType().getCode(),
        p.getStartDate(),
        p.getEndDate(),
        p.getPremium(),
        p.getStatus()
    );
  }

  @Transactional
public PolicyResponse cancel(Long id) {
  var p = policyRepo.findById(id)
      .orElseThrow(() -> new NoSuchElementException("Policy not found: " + id));

  if (!"CANCELLED".equalsIgnoreCase(p.getStatus())) {
    p.setStatus("CANCELLED");
    p = policyRepo.save(p);
  }

  return new PolicyResponse(
      p.getId(),
      p.getClient().getId(),
      p.getPolicyType().getCode(),
      p.getStartDate(),
      p.getEndDate(),
      p.getPremium(),
      p.getStatus()
  );
}
  // prosta kalkulacja: base * liczba_miesięcy + dopłata wiekowa
  private BigDecimal calculatePremium(BigDecimal base, LocalDate birth, LocalDate start, LocalDate end){
    int months = Math.max(1,
        Period.between(start, end).getYears() * 12 + Period.between(start, end).getMonths()
    );
    BigDecimal prem = base.multiply(BigDecimal.valueOf(months));
    int age = Period.between(birth, start).getYears();
    if (age < 26) {
      prem = prem.multiply(BigDecimal.valueOf(1.20));   // +20%
    } else if (age > 60) {
      prem = prem.multiply(BigDecimal.valueOf(1.15));   // +15%
    }
    return prem.setScale(2, java.math.RoundingMode.HALF_UP);
  }
}
