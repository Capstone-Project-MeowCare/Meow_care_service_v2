package com.meow_care.meow_care_service.dto.withdraw;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class RequestWithdrawalCreateDto {
    private UUID userId;
    @NotNull
    private BigDecimal balance;
    private String bankNumber;
    private String fullName;
    private String bankName;
}