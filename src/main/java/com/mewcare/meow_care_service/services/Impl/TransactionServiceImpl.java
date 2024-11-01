package com.mewcare.meow_care_service.services.Impl;

import com.mewcare.meow_care_service.dto.TransactionDto;
import com.mewcare.meow_care_service.entities.Transaction;
import com.mewcare.meow_care_service.mapper.TransactionMapper;
import com.mewcare.meow_care_service.repositories.TransactionRepository;
import com.mewcare.meow_care_service.services.TransactionService;
import com.mewcare.meow_care_service.services.base.BaseServiceImpl;

public class TransactionServiceImpl extends BaseServiceImpl<TransactionDto, Transaction, TransactionRepository, TransactionMapper>
        implements TransactionService {
    public TransactionServiceImpl(TransactionRepository repository, TransactionMapper mapper) {
        super(repository, mapper);
    }
}
