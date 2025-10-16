package com.example.insurance.repository;
import com.example.insurance.domain.PolicyType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PolicyTypeRepository extends JpaRepository<PolicyType, Long> {
  Optional<PolicyType> findByCode(String code);
}
