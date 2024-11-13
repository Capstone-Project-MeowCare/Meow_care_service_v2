package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.Transaction;
import com.meow_care.meow_care_service.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    @Transactional
    @Modifying
    @Query("update Transaction t set t.status = ?1 where t.id = ?2")
    int updateStatusById(TransactionStatus status, UUID id);


}