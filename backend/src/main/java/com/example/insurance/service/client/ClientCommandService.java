package com.example.insurance.service.client;

import com.example.insurance.domain.Client;
import com.example.insurance.dto.client.CreateClientRequest;
import com.example.insurance.dto.client.ClientResponse;
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
        repo.findByEmail(req.email()).ifPresent(_ ->{
            throw new IllegalArgumentException("Client with email already exists: " + req.email());
        });
        Client c = Client.createFrom(req.firstName(), req.lastName(), req.email(), req.birthDate());
        c = repo.save(c);
        return new ClientResponse(c.getId(), c.getFirstName(), c.getLastName(), c.getEmail(), c.getBirthDate(), c.getCreatedAt());        
    }
}
