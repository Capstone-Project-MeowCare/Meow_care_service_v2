package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.Transaction;
import com.meow_care.meow_care_service.enums.PaymentMethod;
import com.meow_care.meow_care_service.enums.TransactionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
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

    @Query("select t from Transaction t where (?1 is null or (t.fromUser.id = ?1 or t.toUser.id = ?1)) and " +
            "(?2 is null or t.status = ?2) and " +
            "(?3 is null or t.paymentMethod = ?3) and " +
            "(?4 is null or t.transactionType = ?4)")
    Page<Transaction> search(@Nullable UUID id, @Nullable TransactionStatus status, @Nullable PaymentMethod paymentMethod, @Nullable String transactionType, Pageable pageable);

    @Query("select t from Transaction t where (?1 is null or (t.fromUser.id = ?1 or t.toUser.id = ?1)) and " +
            "(?2 is null or t.status = ?2) and " +
            "(?3 is null or t.paymentMethod = ?3) and " +
            "(?4 is null or t.transactionType = ?4) and " +
            "t.createdAt between ?5 and ?6")
    Page<Transaction> search(@Nullable UUID id, @Nullable TransactionStatus status, @Nullable PaymentMethod paymentMethod, @Nullable String transactionType, Instant fromTime, Instant toTime, Pageable pageable);

}