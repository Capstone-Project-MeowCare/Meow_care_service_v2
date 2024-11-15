package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.TransactionDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.Transaction;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.enums.PaymentMethod;
import com.meow_care.meow_care_service.enums.TransactionStatus;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.TransactionMapper;
import com.meow_care.meow_care_service.repositories.TransactionRepository;
import com.meow_care.meow_care_service.services.TransactionService;
import com.meow_care.meow_care_service.services.WalletService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionServiceImpl extends BaseServiceImpl<TransactionDto, Transaction, TransactionRepository, TransactionMapper> implements TransactionService {

    private final UUID ADMIN_ID = UUID.fromString("e7b8f9a6-5678-4c56-89a7-23456789abcd"); // or another fixed ID

    private final WalletService walletService;

    public TransactionServiceImpl(TransactionRepository repository, TransactionMapper mapper, WalletService walletService) {
        super(repository, mapper);
        this.walletService = walletService;
    }

    @Override
    public Transaction create(Transaction transaction) {
        return repository.save(transaction);
    }

    @Override
    public void updateStatus(UUID id, TransactionStatus status) {
        if (!repository.existsById(id)) {
            throw new ApiException(ApiStatus.ERROR, "Transaction not found");
        }

        if (repository.updateStatusById(status, id) == 0) {
            throw new ApiException(ApiStatus.ERROR, "Transaction status not updated");
        }

        if (status == TransactionStatus.COMPLETED) {
            Transaction transaction = repository.findById(id).orElseThrow();

            if (transaction.getPaymentMethod() == PaymentMethod.WALLET) {
                transfer(transaction.getFromUser().getId(), transaction.getToUser().getId(), transaction.getAmount());
            } else {
                walletService.addHoldBalance(transaction.getFromUser().getId(), transaction.getAmount());
            }
        }
    }

    @Override
    public void transfer(UUID fromUserId, UUID toUserId, BigDecimal amount) {
        walletService.transfer(fromUserId, toUserId, amount);
    }

    //create commission transaction
    @Override
    public void createCommissionTransaction(UUID userId, UUID bookingId, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setBookingId(bookingId);
        transaction.setFromUserId(userId);
        transaction.setToUserId(ADMIN_ID);
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setTransactionType("COMMISSION");
        transaction.setPaymentMethod(PaymentMethod.WALLET);
        repository.save(transaction);
    }

    @Override
    public void completeService(UUID bookingId, BigDecimal amount) {
        List<Transaction> transactions = repository.findByBookingId(bookingId);

        if (transactions.isEmpty()) {
            throw new ApiException(ApiStatus.ERROR, "Transaction not found");
        }

        BigDecimal total = transactions.stream().filter(transaction -> transaction.getStatus() == TransactionStatus.COMPLETED).map(Transaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        if (total.compareTo(amount) < 0) {
            throw new ApiException(ApiStatus.ERROR, "Transaction amount is not enough");
        }

        walletService.holdBalanceToBalance(transactions.get(0).getFromUser().getId(), amount);
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

}
