package com.meow_care.meow_care_service.dto.contract;

import lombok.Builder;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link com.meow_care.meow_care_service.entities.ContractTemplate}
 */
@Builder
public record ContractTemplateResponseDto(
        UUID id,
        String name,
        String content,
        List<String> contractFields,
        Instant createdAt,
        Instant updatedAt
) {
}