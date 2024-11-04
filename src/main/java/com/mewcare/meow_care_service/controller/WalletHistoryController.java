package com.mewcare.meow_care_service.controller;

import com.mewcare.meow_care_service.dto.WalletHistoryDto;
import com.mewcare.meow_care_service.dto.response.ApiResponse;
import com.mewcare.meow_care_service.services.WalletHistoryService;
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
@RequestMapping("/wallet-histories")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class WalletHistoryController {

    private final WalletHistoryService walletHistoryService;

    @GetMapping("/{id}")
    public ApiResponse<WalletHistoryDto> getWalletHistoryById(@PathVariable UUID id) {
        return walletHistoryService.get(id);
    }

    @GetMapping
    public ApiResponse<List<WalletHistoryDto>> getAllWalletHistories() {
        return walletHistoryService.getAll();
    }

    @PostMapping
    public ApiResponse<WalletHistoryDto> createWalletHistory(@RequestBody WalletHistoryDto walletHistoryDto) {
        return walletHistoryService.create(walletHistoryDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<WalletHistoryDto> updateWalletHistory(@PathVariable UUID id, @RequestBody WalletHistoryDto walletHistoryDto) {
        return walletHistoryService.update(id, walletHistoryDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteWalletHistory(@PathVariable UUID id) {
        return walletHistoryService.delete(id);
    }
}
