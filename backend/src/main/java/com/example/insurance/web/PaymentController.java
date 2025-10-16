package com.example.insurance.web;

import com.example.insurance.dto.PaymentDtos.*;
import com.example.insurance.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policies/{policyId}/payments")
public class PaymentController {

  private final PaymentService service;

  public PaymentController(PaymentService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<PaymentResponse> add(@PathVariable Long policyId,
                                             @Valid @RequestBody CreatePaymentRequest req) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.add(policyId, req));
  }

  @GetMapping
  public List<PaymentResponse> list(@PathVariable Long policyId) {
    return service.list(policyId);
  }
}
