package com.meow_care.meow_care_service.dto.withdraw;

import com.meow_care.meow_care_service.entities.RequestWithdrawal;
import com.meow_care.meow_care_service.enums.WithdrawStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link RequestWithdrawal}
 */

@Getter
@AllArgsConstructor
@Builder
public class RequestWithdrawalDto {
    private UUID id;
    @NotNull
    private BigDecimal balance;
    @NotNull
    private Instant createAt;
    private Instant updatedAt;
    @NotNull
    private WithdrawStatus processStatus;
    private boolean Deleted;
    private String bankNumber;
    private String fullName;
    private String bankName;
}