package com.example.insurance.service.client;

import com.example.insurance.domain.Client;
import com.example.insurance.dto.ClientDtos.CreateClientRequest;
import com.example.insurance.dto.ClientDtos.ClientResponse;
import com.example.insurance.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientCommandService {

  private final ClientRepository repo;

  @Transactional
  public ClientResponse create(CreateClientRequest req) {
    
   if (repo.existsByEmail(req.email())) {
    throw new IllegalArgumentException("Client with email already exists: " + req.email());
  }
    Client c = Client.createFrom(req.firstName(), req.lastName(), req.email(), req.birthDate());
    c = repo.save(c);
    return toResp(c);
  }

  private static ClientResponse toResp(Client c) {
    return new ClientResponse(c.getId(), c.getFirstName(), c.getLastName(), c.getEmail(), c.getBirthDate(), c.getCreatedAt());
  }
}
