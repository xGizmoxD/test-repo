package com.example.insurance.service;

import com.example.insurance.domain.*;
import com.example.insurance.dto.*;
import com.example.insurance.repository.*;
import com.example.insurance.service.premium.PremiumStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PolicyService {

  private final PolicyRepository policyRepo;
  private final PolicyTypeRepository typeRepo;
  private final ClientRepository clientRepo;
  private final PremiumStrategy premiumStrategy; // <— wstrzykujemy strategię

  @Transactional
  public PolicyResponse create(CreatePolicyRequest r){
    Client client = clientRepo.findById(r.clientId())
        .orElseThrow(() -> new NoSuchElementException("Client not found: " + r.clientId()));
    PolicyType type = typeRepo.findByCode(r.policyTypeCode())
        .orElseThrow(() -> new NoSuchElementException("PolicyType not found: " + r.policyTypeCode()));

    if (r.endDate().isBefore(r.startDate())) {
      throw new IllegalArgumentException("endDate must be after startDate");
    }

    BigDecimal premium = premiumStrategy.calculate(type.getBasePremium(), client.getBirthDate(), r.startDate(), r.endDate());

    Policy p = new Policy();
    p.setClient(client);
    p.setPolicyType(type);
    p.setStartDate(r.startDate());
    p.setEndDate(r.endDate());
    p.setPremium(premium);
    p.setStatus("ACTIVE");

    p = policyRepo.save(p);
    return new PolicyResponse(
        p.getId(), client.getId(), type.getCode(),
        p.getStartDate(), p.getEndDate(), p.getPremium(), p.getStatus()
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
        .orElseThrow(() -> new NoSuchElementException("Policy not found: " + id));

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
}
