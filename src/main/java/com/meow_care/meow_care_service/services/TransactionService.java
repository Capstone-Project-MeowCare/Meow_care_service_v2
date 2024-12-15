package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.TransactionDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.Transaction;
import com.meow_care.meow_care_service.enums.PaymentMethod;
import com.meow_care.meow_care_service.enums.TransactionStatus;
import com.meow_care.meow_care_service.enums.TransactionType;
import com.meow_care.meow_care_service.services.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface TransactionService extends BaseService<TransactionDto, Transaction> {
    Transaction create(Transaction transaction);

    void updateTransactionToHolding(UUID id, Long transId);

    //update status of transaction
    void updateStatus(UUID id, TransactionStatus status);

    void transfer(UUID fromUserId, UUID toUserId, BigDecimal amount);

    //create transaction and transfer money to wallet
    void createPaymentTransactionAndTransFer(UUID fromUserId, UUID toUserId, UUID bookingId, TransactionStatus status, TransactionType transactionType, PaymentMethod paymentMethod, BigDecimal amount);

    //create commission transaction
    void createCommissionTransaction(UUID userId, UUID bookingId, BigDecimal amount);

    void completeService(UUID bookingId, BigDecimal amount);

    ApiResponse<List<TransactionDto>> getByUserId(UUID userId);

    ApiResponse<Page<TransactionDto>> search(UUID userId, TransactionStatus status, PaymentMethod paymentMethod, TransactionType transactionType, Instant fromTime, Instant toTime, Pageable pageable);

    ApiResponse<BigDecimal> calculateTotalAmount(UUID userId, TransactionStatus status, PaymentMethod paymentMethod, TransactionType transactionType, Instant fromTime, Instant toTime);

    //refund transaction by bookingId
    @Transactional
    void refund(UUID bookingId);
}
