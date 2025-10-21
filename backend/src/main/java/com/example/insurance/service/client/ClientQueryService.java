package com.example.insurance.service.client;

import com.example.insurance.domain.Client;
import com.example.insurance.dto.client.ClientResponse;
import com.example.insurance.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientQueryService {

  private final ClientRepository repo;

  @Transactional(readOnly = true)
  public ClientResponse get(Long id) {
    Client c = repo.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Client not found: " + id));
    return toResp(c);
  }

  private static ClientResponse toResp(Client c) {
    return new ClientResponse(
        c.getId(),
        c.getFirstName(),
        c.getLastName(),
        c.getEmail(),
        c.getBirthDate(),
        c.getCreatedAt()
    );
  }
}
