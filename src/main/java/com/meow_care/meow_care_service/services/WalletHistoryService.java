package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.WalletHistoryDto;
import com.meow_care.meow_care_service.entities.Transaction;
import com.meow_care.meow_care_service.entities.WalletHistory;
import com.meow_care.meow_care_service.services.base.BaseService;

import java.util.List;

public interface WalletHistoryService extends BaseService<WalletHistoryDto, WalletHistory> {
    //create wallet history from transaction
    List<WalletHistory> create(Transaction transaction);
}
