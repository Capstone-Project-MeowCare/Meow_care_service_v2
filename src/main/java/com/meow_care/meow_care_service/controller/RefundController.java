package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.RefundDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.services.RefundService;
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
@RequestMapping("/refunds")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class RefundController {

    private final RefundService refundService;

    @GetMapping("/{id}")
    public ApiResponse<RefundDto> getRefundById(@PathVariable UUID id) {
        return refundService.get(id);
    }

    @GetMapping
    public ApiResponse<List<RefundDto>> getAllRefunds() {
        return refundService.getAll();
    }

    @PostMapping
    public ApiResponse<RefundDto> createRefund(@RequestBody RefundDto refundDto) {
        return refundService.create(refundDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<RefundDto> updateRefund(@PathVariable UUID id, @RequestBody RefundDto refundDto) {
        return refundService.update(id, refundDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteRefund(@PathVariable UUID id) {
        return refundService.delete(id);
    }
}
