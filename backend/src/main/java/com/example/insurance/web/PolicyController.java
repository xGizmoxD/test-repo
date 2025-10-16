package com.example.insurance.web;

import com.example.insurance.dto.*;
import com.example.insurance.service.PolicyService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


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

  @GetMapping
  public Page<PolicyResponse> list(
      @RequestParam(required = false) Long clientId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id,desc") String sort
  ) {
    // sort w formacie: field,dir np. "id,desc" albo "startDate,asc"
    String[] parts = sort.split(",", 2);
    Sort s = Sort.by(
        Sort.Direction.fromString(parts.length > 1 ? parts[1] : "desc"),
        parts[0]
    );
    return service.list(clientId, PageRequest.of(page, size, s));
  }

  @PostMapping("/{id}/cancel")
public PolicyResponse cancel(@PathVariable Long id) {
  return service.cancel(id);
}

}


