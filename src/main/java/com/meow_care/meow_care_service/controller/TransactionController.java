package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.TransactionDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.enums.PaymentMethod;
import com.meow_care.meow_care_service.enums.TransactionStatus;
import com.meow_care.meow_care_service.enums.TransactionType;
import com.meow_care.meow_care_service.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
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

    //search by time range, status, userId, paymentMethod and transactionType
    @GetMapping("/search/pagination")
    public ApiResponse<Page<TransactionDto>> searchTransactionsWithPagination(@RequestParam(required = false) UUID userId,
                                                                              @RequestParam(required = false) TransactionStatus status,
                                                                              @RequestParam(required = false) PaymentMethod paymentMethod,
                                                                              @RequestParam(required = false) TransactionType transactionType,
                                                                              @RequestParam(required = false) Instant fromTime,
                                                                              @RequestParam(required = false) Instant toTime,
                                                                              @RequestParam(defaultValue = "1") int page,
                                                                              @RequestParam(defaultValue = "10") int size,
                                                                              @RequestParam(defaultValue = "createdAt") String sort,
                                                                              @RequestParam(defaultValue = "DESC") Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, sort));
        return transactionService.search(userId, status, paymentMethod, transactionType, fromTime, toTime, pageable);
    }

    //get by user id
    @GetMapping("/user/{userId}")
    public ApiResponse<List<TransactionDto>> getTransactionsByUserId(@PathVariable UUID userId) {
        return transactionService.getByUserId(userId);
    }

    @PutMapping("/{id}")
    public ApiResponse<TransactionDto> updateTransaction(@PathVariable UUID id, @RequestBody TransactionDto transactionDto) {
        return transactionService.update(id, transactionDto);
    }

    //update transaction status
    @PutMapping("/{id}/status")
    public ApiResponse<Void> updateTransactionStatus(@PathVariable UUID id, @RequestParam TransactionStatus status) {
        transactionService.updateStatus(id, status);
        return ApiResponse.updated();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteTransaction(@PathVariable UUID id) {
        return transactionService.delete(id);
    }
}
