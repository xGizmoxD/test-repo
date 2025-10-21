package com.example.insurance.service;

import com.example.insurance.domain.*;
import com.example.insurance.dto.*;
import com.example.insurance.repository.*;
import com.example.insurance.service.premium.PremiumStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class PolicyService {
  private final PolicyRepository policyRepo;
  private final PolicyTypeRepository typeRepo;
  private final ClientRepository clientRepo;
  private final PremiumStrategy premiumStrategy;   

  
  public PolicyService(PolicyRepository p,
                       PolicyTypeRepository t,
                       ClientRepository c,
                       PremiumStrategy premiumStrategy) {
    this.policyRepo = p;
    this.typeRepo = t;
    this.clientRepo = c;
    this.premiumStrategy = premiumStrategy;
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

    
    BigDecimal premium = premiumStrategy.calculate(
        type.getBasePremium(),
        client.getBirthDate(),
        r.startDate(),
        r.endDate()
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
        p.getId(), client.getId(), type.getCode(),
        p.getStartDate(), p.getEndDate(), p.getPremium(), p.getStatus()
    );
  }

  public PolicyResponse get(Long id){
    Policy p = policyRepo.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Policy not found"));
    return new PolicyResponse(
        p.getId(), p.getClient().getId(), p.getPolicyType().getCode(),
        p.getStartDate(), p.getEndDate(), p.getPremium(), p.getStatus()
    );
  }
}
