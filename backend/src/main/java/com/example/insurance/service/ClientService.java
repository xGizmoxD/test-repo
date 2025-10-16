package com.example.insurance.service;

import com.example.insurance.domain.Client;
import com.example.insurance.dto.ClientResponse;
import com.example.insurance.dto.CreateClientRequest;
import com.example.insurance.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {
  private final ClientRepository repo;

  public ClientService(ClientRepository repo) { this.repo = repo; }

  @Transactional
  public ClientResponse create(CreateClientRequest req){
    if (repo.existsByEmail(req.email())) {
      throw new IllegalArgumentException("Email already in use");
    }
    Client c = new Client();
    c.setFirstName(req.firstName());
    c.setLastName(req.lastName());
    c.setEmail(req.email());
    c.setBirthDate(req.birthDate());
    c = repo.save(c);
    return toResp(c);
  }

  public ClientResponse get(Long id){
    Client c = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Client not found"));
    return toResp(c);
  }

  private ClientResponse toResp(Client c){
    return new ClientResponse(
      c.getId(), c.getFirstName(), c.getLastName(), c.getEmail(), c.getBirthDate(), c.getCreatedAt()
    );
  }
}