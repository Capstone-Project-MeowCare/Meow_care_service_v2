package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.MomoPaymentReturnDto;
import com.meow_care.meow_care_service.dto.TransactionDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.Transaction;
import com.meow_care.meow_care_service.services.base.BaseService;

public interface TransactionService extends BaseService<TransactionDto, Transaction> {
    Transaction create(Transaction transaction);

    ApiResponse<Void> momoCallback(MomoPaymentReturnDto momoPaymentReturnDto);
}
