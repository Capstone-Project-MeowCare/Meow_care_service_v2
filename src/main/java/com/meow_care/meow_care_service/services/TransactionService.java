package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.TransactionDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.Transaction;
import com.meow_care.meow_care_service.enums.PaymentMethod;
import com.meow_care.meow_care_service.enums.TransactionStatus;
import com.meow_care.meow_care_service.services.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface TransactionService extends BaseService<TransactionDto, Transaction> {
    Transaction create(Transaction transaction);

    //update status of transaction
    void updateStatus(UUID id, TransactionStatus status);

    void transfer(UUID fromUserId, UUID toUserId, BigDecimal amount);


    //create commission transaction
    void createCommissionTransaction(UUID userId, UUID bookingId, BigDecimal amount);

    void completeService(UUID bookingId, BigDecimal amount);

    ApiResponse<List<TransactionDto>> getByUserId(UUID userId);

    ApiResponse<Page<TransactionDto>> search(UUID userId, TransactionStatus status, PaymentMethod paymentMethod, String transactionType, Instant fromTime, Instant toTime, Pageable pageable);

}
