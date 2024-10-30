package com.mewcare.meow_care_service.repositories;

import com.mewcare.meow_care_service.entities.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContractRepository extends JpaRepository<Contract, UUID> {
}