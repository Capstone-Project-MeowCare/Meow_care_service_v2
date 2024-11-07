package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.WalletDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/wallets")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/{id}")
    public ApiResponse<WalletDto> getWalletById(@PathVariable UUID id) {
        return walletService.get(id);
    }

    @GetMapping
    public ApiResponse<List<WalletDto>> getAllWallets() {
        return walletService.getAll();
    }

    @PostMapping
    public ApiResponse<WalletDto> createWallet(@RequestBody WalletDto walletDto) {
        return walletService.create(walletDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<WalletDto> updateWallet(@PathVariable UUID id, @RequestBody WalletDto walletDto) {
        return walletService.update(id, walletDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteWallet(@PathVariable UUID id) {
        return walletService.delete(id);
    }
}
