package com.example.insurance.web;

import com.example.insurance.dto.client.ClientResponse;
import com.example.insurance.dto.client.CreateClientRequest;
import com.example.insurance.service.client.ClientCommandService;
import com.example.insurance.service.client.ClientQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

  private final ClientCommandService command;
  private final ClientQueryService query;

  @PostMapping
  public ResponseEntity<ClientResponse> create(@Valid @RequestBody CreateClientRequest req) {
    var resp = command.create(req);
    return ResponseEntity.status(HttpStatus.CREATED).body(resp);
  }

  @GetMapping("/{id}")
  public ClientResponse get(@PathVariable Long id) {
    return query.get(id);
  }
}
