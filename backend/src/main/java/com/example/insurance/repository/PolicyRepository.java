package com.example.insurance.repository;

import com.example.insurance.domain.Policy;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
  Page<Policy> findByClient_Id(Long clientId, Pageable pageable);
}

