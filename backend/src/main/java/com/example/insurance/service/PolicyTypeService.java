package com.example.insurance.service;

import java.util.NoSuchElementException;
import org.springframework.transaction.annotation.Transactional;
import com.example.insurance.domain.PolicyType;
import com.example.insurance.dto.PolicyTypeDtos.CreatePolicyTypeRequest;
import com.example.insurance.dto.PolicyTypeDtos.PolicyTypeResponse;
import com.example.insurance.repository.PolicyTypeRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class PolicyTypeService {

  private final PolicyTypeRepository repo;

  public PolicyTypeService(PolicyTypeRepository repo) {
    this.repo = repo;
  }

  @Transactional
  public PolicyTypeResponse create(CreatePolicyTypeRequest req) {
    if (repo.existsByCode(req.code())) {
      throw new IllegalArgumentException("Policy type with code already exists: " + req.code());
    }
    var entity = PolicyType.builder()
        .code(req.code())
        .name(req.name())
        .basePremium(req.basePremium())
        .build();

    var saved = repo.save(entity);
    return toResponse(saved);
  }

  @Transactional(readOnly = true)
  public List<PolicyTypeResponse> listAll() {
    return repo.findAll().stream().map(this::toResponse).toList();
  }

  private PolicyTypeResponse toResponse(PolicyType p) {
    return new PolicyTypeResponse(p.getId(), p.getCode(), p.getName(), p.getBasePremium());
  }
  @Transactional(readOnly = true)
  public PolicyTypeResponse findById(Long id) {
  var e = repo.findById(id)
      .orElseThrow(() -> new NoSuchElementException("PolicyType not found: " + id));
  return new PolicyTypeResponse(e.getId(), e.getCode(), e.getName(), e.getBasePremium());
}
}
