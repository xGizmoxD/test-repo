package com.example.insurance.web;

import com.example.insurance.dto.ClientDtos.CreateClientRequest;
import com.example.insurance.dto.ClientDtos.ClientResponse;
import com.example.insurance.service.client.ClientCommandService;
import com.example.insurance.service.client.ClientQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

  private final ClientCommandService command;
  private final ClientQueryService query;

  @PostMapping
  public ClientResponse create(@Valid @RequestBody CreateClientRequest req) {
    return command.create(req);
  }

  @GetMapping("/{id}")
  public ClientResponse get(@PathVariable Long id) {
    return query.get(id);
  }
}
