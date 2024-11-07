package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.TransactionDto;
import com.meow_care.meow_care_service.entities.Transaction;
import com.meow_care.meow_care_service.mapper.TransactionMapper;
import com.meow_care.meow_care_service.repositories.TransactionRepository;
import com.meow_care.meow_care_service.services.TransactionService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl extends BaseServiceImpl<TransactionDto, Transaction, TransactionRepository, TransactionMapper>
        implements TransactionService {
    public TransactionServiceImpl(TransactionRepository repository, TransactionMapper mapper) {
        super(repository, mapper);
    }
}
