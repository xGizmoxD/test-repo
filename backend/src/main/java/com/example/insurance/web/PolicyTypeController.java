package com.example.insurance.web;

import com.example.insurance.dto.PolicyTypeDtos.CreatePolicyTypeRequest;
import com.example.insurance.dto.PolicyTypeDtos.PolicyTypeResponse;
import com.example.insurance.service.PolicyTypeService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policy-types")
public class PolicyTypeController {

  private final PolicyTypeService service;

  public PolicyTypeController(PolicyTypeService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<PolicyTypeResponse> create(@Valid @RequestBody CreatePolicyTypeRequest req) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
  }

  @GetMapping
  public List<PolicyTypeResponse> listAll() {
    return service.listAll();
  }

  @GetMapping("/{id}")
  public PolicyTypeResponse getOne(@PathVariable Long id) {
    return service.findById(id);
  }
}
