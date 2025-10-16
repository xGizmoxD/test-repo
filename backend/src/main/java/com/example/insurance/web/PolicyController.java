package com.example.insurance.web;

import com.example.insurance.dto.*;
import com.example.insurance.service.PolicyService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {
  private final PolicyService service;
  public PolicyController(PolicyService service){ this.service = service; }

  @PostMapping
  public ResponseEntity<PolicyResponse> create(@Valid @RequestBody CreatePolicyRequest req){
    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
  }

  @GetMapping("/{id}")
  public PolicyResponse get(@PathVariable Long id){ return service.get(id); }
}
