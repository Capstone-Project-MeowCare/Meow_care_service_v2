package com.mewcare.meow_care_service.repositories;

import com.mewcare.meow_care_service.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}