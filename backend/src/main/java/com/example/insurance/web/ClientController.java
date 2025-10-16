package com.example.insurance.web;

import com.example.insurance.dto.ClientResponse;
import com.example.insurance.dto.CreateClientRequest;
import com.example.insurance.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
  private final ClientService service;
  public ClientController(ClientService service) { this.service = service; }

  @PostMapping
  public ResponseEntity<ClientResponse> create(@Valid @RequestBody CreateClientRequest req){
    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
  }

  @GetMapping("/{id}")
  public ClientResponse get(@PathVariable Long id){
    return service.get(id);
  }
}