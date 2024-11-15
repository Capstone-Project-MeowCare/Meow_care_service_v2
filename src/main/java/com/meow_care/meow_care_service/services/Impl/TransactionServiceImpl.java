package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.TransactionDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.Transaction;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.enums.TransactionStatus;
import com.meow_care.meow_care_service.mapper.TransactionMapper;
import com.meow_care.meow_care_service.repositories.TransactionRepository;
import com.meow_care.meow_care_service.services.TransactionService;
import com.meow_care.meow_care_service.services.WalletService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class TransactionServiceImpl extends BaseServiceImpl<TransactionDto, Transaction, TransactionRepository, TransactionMapper>
        implements TransactionService {

    private final UUID SYSTEM_WALLET_ID = UUID.fromString("00000000-0000-0000-0000-000000000001"); // or another fixed ID

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

        if (repository.existsById(id)) {
            ApiResponse.error(ApiStatus.ERROR, "Transaction not found");
        }

        int updated = repository.updateStatusById(status, id);

        if (updated == 0) {
            ApiResponse.error(ApiStatus.ERROR, "Update status failed");
        }

        if (status == TransactionStatus.COMPLETED) {
            Transaction transaction = repository.findById(id).orElse(null);
            transfer(transaction.getFromUser().getId(), transaction.getToUser().getId(), transaction.getAmount());
        }
    }

    @Override
    public void transfer(UUID fromUserId, UUID toUserId, BigDecimal amount) {
        walletService.transfer(fromUserId, toUserId, amount);
    }

    //create commission transaction
    public void createCommissionTransaction(UUID userId, UUID bookingId, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setFromUserId(userId);
        transaction.setToUserId(SYSTEM_WALLET_ID);
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setTransactionType("COMMISSION");
        repository.save(transaction);
    }

}
