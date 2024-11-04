package com.mewcare.meow_care_service.controller;

import com.mewcare.meow_care_service.dto.TransactionDto;
import com.mewcare.meow_care_service.dto.response.ApiResponse;
import com.mewcare.meow_care_service.services.TransactionService;
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
@RequestMapping("/transactions")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/{id}")
    public ApiResponse<TransactionDto> getTransactionById(@PathVariable UUID id) {
        return transactionService.get(id);
    }

    @GetMapping
    public ApiResponse<List<TransactionDto>> getAllTransactions() {
        return transactionService.getAll();
    }

    @PostMapping
    public ApiResponse<TransactionDto> createTransaction(@RequestBody TransactionDto transactionDto) {
        return transactionService.create(transactionDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<TransactionDto> updateTransaction(@PathVariable UUID id, @RequestBody TransactionDto transactionDto) {
        return transactionService.update(id, transactionDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteTransaction(@PathVariable UUID id) {
        return transactionService.delete(id);
    }
}
