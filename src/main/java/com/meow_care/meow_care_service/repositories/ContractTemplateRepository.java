package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.ContractTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface ContractTemplateRepository extends JpaRepository<ContractTemplate, UUID> {
    @Transactional
    @Query("select c from ContractTemplate c where c.name = ?1")
    Optional<ContractTemplate> findByName(String name);
}