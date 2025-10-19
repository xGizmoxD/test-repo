package com.example.insurance.service.client;

import com.example.insurance.domain.Client;
import com.example.insurance.dto.ClientDtos.ClientResponse;
import com.example.insurance.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ClientQueryService {

  private final ClientRepository repo;

  @Transactional(readOnly = true)
  public ClientResponse get(Long id) {
    Client c = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Client not found: " + id));
    return toResp(c);
  }

  @Transactional(readOnly = true)
  public Page<ClientResponse> list(Pageable pageable) {
    return repo.findAll(pageable).map(ClientQueryService::toResp);
  }

  private static ClientResponse toResp(Client c) {
    return new ClientResponse(c.getId(), c.getFirstName(), c.getLastName(), c.getEmail(), c.getBirthDate(), c.getCreatedAt());
  }
}
