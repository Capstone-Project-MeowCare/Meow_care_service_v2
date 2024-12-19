package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.WalletDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.Wallet;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.WalletMapper;
import com.meow_care.meow_care_service.repositories.WalletRepository;
import com.meow_care.meow_care_service.services.WalletService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class WalletServiceImpl extends BaseServiceImpl<WalletDto, Wallet, WalletRepository, WalletMapper> implements WalletService {

    public WalletServiceImpl(WalletRepository repository, WalletMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public void transfer(UUID fromWalletId, UUID toWalletId, BigDecimal amount) {
        Wallet fromWallet = repository.findByUserId(fromWalletId).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Wallet not found"));
        Wallet toWallet = repository.findByUserId(toWalletId).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Wallet not found"));

        if (fromWallet.getBalance().compareTo(amount) < 0) {
            throw new ApiException(ApiStatus.AMOUNT_NOT_ENOUGH, "Amount not enough");
        }

        fromWallet.setBalance(fromWallet.getBalance().subtract(amount));
        toWallet.addHoldBalance(amount);
        repository.save(fromWallet);
    }

    @Override
    public void addHoldBalance(UUID userId, BigDecimal amount) {
        Wallet wallet = repository.findByUserId(userId).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Wallet not found"));
        wallet.addHoldBalance(amount);
        repository.save(wallet);
    }

    @Override
    public void holdBalanceToBalance(UUID userId, BigDecimal amount) {
        Wallet wallet = repository.findByUserId(userId).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Wallet not found"));

        // check hold balance
        if (wallet.getHoldBalance().compareTo(amount) < 0) {
            throw new ApiException(ApiStatus.AMOUNT_NOT_ENOUGH, "Amount not enough");
        }

        wallet.holdBalanceToBalance(amount);
        repository.save(wallet);
    }

    @Override
    public ApiResponse<WalletDto> getByUserId(UUID userId) {

        Wallet wallet = repository.findByUserId(userId).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Wallet not found"));
        return ApiResponse.success(mapper.toDto(wallet));

    }

    @Override
    public void addBalance(UUID userId, BigDecimal amount) {
        Wallet wallet = repository.findByUserId(userId).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Wallet not found"));
        wallet.setBalance(wallet.getBalance().add(amount));
        repository.save(wallet);
    }

    @Override
    public BigDecimal getBalance(UUID userId) {
        Wallet wallet = repository.findByUserId(userId).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Wallet not found"));
        return wallet.getBalance();
    }
}
