package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.TransactionDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.Transaction;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.enums.PaymentMethod;
import com.meow_care.meow_care_service.enums.TransactionStatus;
import com.meow_care.meow_care_service.enums.TransactionType;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.TransactionMapper;
import com.meow_care.meow_care_service.repositories.TransactionRepository;
import com.meow_care.meow_care_service.services.TransactionService;
import com.meow_care.meow_care_service.services.WalletHistoryService;
import com.meow_care.meow_care_service.services.WalletService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionServiceImpl extends BaseServiceImpl<TransactionDto, Transaction, TransactionRepository, TransactionMapper> implements TransactionService {

    private final UUID ADMIN_ID = UUID.fromString("e7b8f9a6-5678-4c56-89a7-23456789abcd"); // or another fixed ID

    private final WalletService walletService;

    private final WalletHistoryService walletHistoryService;

    public TransactionServiceImpl(TransactionRepository repository, TransactionMapper mapper, WalletService walletService, WalletHistoryService walletHistoryService) {
        super(repository, mapper);
        this.walletService = walletService;
        this.walletHistoryService = walletHistoryService;
    }

    @Override
    public Transaction create(Transaction transaction) {
        return repository.save(transaction);
    }

    @Override
    @Transactional
    public void updateStatus(UUID id, TransactionStatus status) {
        if (!repository.existsById(id)) {
            throw new ApiException(ApiStatus.ERROR, "Transaction not found");
        }

        if (updateStatusById(id, status) == 0) {
            throw new ApiException(ApiStatus.ERROR, "Transaction status not updated");
        }
    }

    @Override
    public void transfer(UUID fromUserId, UUID toUserId, BigDecimal amount) {
        walletService.transfer(fromUserId, toUserId, amount);
    }

    //create commission transaction
    @Override
    @Transactional
    public void createCommissionTransaction(UUID userId, UUID bookingId, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setBookingId(bookingId);
        transaction.setFromUserId(userId);
        transaction.setToUserId(ADMIN_ID);
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setTransactionType(TransactionType.COMMISSION);
        transaction.setPaymentMethod(PaymentMethod.WALLET);
        transaction = create(transaction);
        updateStatusById(transaction.getId(), TransactionStatus.COMPLETED);
    }

    @Override
    public void completeService(UUID bookingId, BigDecimal amount) {
        List<Transaction> transactions = repository.findByBookingId(bookingId);

        if (transactions.isEmpty()) {
            throw new ApiException(ApiStatus.ERROR, "Transaction not found");
        }

        for (Transaction transaction : transactions) {
            if (transaction.getStatus() == TransactionStatus.HOLDING) {
                updateStatusById(transaction.getId(), TransactionStatus.COMPLETED);
            }
        }

    }

    @Override
    public ApiResponse<List<TransactionDto>> getByUserId(UUID userId) {
        List<Transaction> transactions = repository.findByFromUser_Id(userId);
        transactions.addAll(repository.findByToUser_Id(userId));

        if (transactions.isEmpty()) {
            throw new ApiException(ApiStatus.ERROR, "Transaction not found");
        }

        return ApiResponse.success(mapper.toDtoList(transactions));
    }

    @Override
    public ApiResponse<Page<TransactionDto>> search(UUID userId, TransactionStatus status, PaymentMethod paymentMethod, String transactionType, Instant fromTime, Instant toTime, Pageable pageable) {
        if ((fromTime == null) != (toTime == null) || (fromTime != null && fromTime.isAfter(toTime))) {
            throw new ApiException(ApiStatus.ERROR, "Invalid time range");
        }

        Page<Transaction> transactions = (fromTime == null)
                ? repository.search(userId, status, paymentMethod, transactionType, pageable)
                : repository.search(userId, status, paymentMethod, transactionType, fromTime, toTime, pageable);

        Page<TransactionDto> transactionDtos = transactions.map(mapper::toDto);
        return ApiResponse.success(transactionDtos);
    }

    private int updateStatusById(UUID id, TransactionStatus status) {

        if (status == TransactionStatus.HOLDING){
            Transaction transaction = repository.findById(id).orElseThrow();
            walletService.addHoldBalance(transaction.getToUser().getId(), transaction.getAmount());
        }

        if (status == TransactionStatus.COMPLETED) {
            Transaction transaction = repository.findById(id).orElseThrow();

            //transfer money to user
            if (transaction.getPaymentMethod() == PaymentMethod.WALLET) {
                transfer(transaction.getFromUser().getId(), transaction.getToUser().getId(), transaction.getAmount());
            } else {
                walletService.holdBalanceToBalance(transaction.getToUser().getId(), transaction.getAmount());
            }

            //create wallet history
            walletHistoryService.create(transaction);
        }

        return repository.updateStatusById(status, id);
    }
}
