package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.Transaction;
import com.meow_care.meow_care_service.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    @Transactional
    @Modifying
    @Query("update Transaction t set t.status = ?1 where t.id = ?2")
    int updateStatusById(TransactionStatus status, UUID id);

    @Query("select t from Transaction t where t.booking.id = ?1")
    List<Transaction> findByBookingId(UUID id);

    @Query("select t from Transaction t where t.fromUser.id = ?1")
    List<Transaction> findByFromUser_Id(UUID id);

    @Query("select t from Transaction t where t.toUser.id = ?1")
    List<Transaction> findByToUser_Id(UUID id);

}