package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.WalletDto;
import com.meow_care.meow_care_service.entities.Wallet;
import com.meow_care.meow_care_service.services.base.BaseService;

import java.math.BigDecimal;
import java.util.UUID;

public interface WalletService extends BaseService<WalletDto, Wallet> {
    void transfer(UUID id, UUID id1, BigDecimal amount);
}
