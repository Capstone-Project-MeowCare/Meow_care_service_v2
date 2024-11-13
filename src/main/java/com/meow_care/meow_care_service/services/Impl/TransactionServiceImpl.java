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
        int updated = repository.updateStatusById(status, id);

        if (updated == 0) {
            ApiResponse.error(ApiStatus.ERROR, "Update status failed");
        }
    }

    @Override
    public void transfer(UUID fromUserId, UUID toUserId, BigDecimal amount) {
        walletService.transfer(fromUserId, toUserId, amount);
    }
}
