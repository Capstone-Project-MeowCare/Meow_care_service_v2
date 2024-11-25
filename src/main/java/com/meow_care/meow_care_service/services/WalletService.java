package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.WalletDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.Wallet;
import com.meow_care.meow_care_service.services.base.BaseService;

import java.math.BigDecimal;
import java.util.UUID;

public interface WalletService extends BaseService<WalletDto, Wallet> {
    void transfer(UUID fromWalletId, UUID toWalletId, BigDecimal amount);

    //add hold balance
    void addHoldBalance(UUID userId, BigDecimal amount);

    //hold balance to balance when booking is completed
    void holdBalanceToBalance(UUID userId, BigDecimal amount);

    ApiResponse<WalletDto> getByUserId(UUID userId);
}
