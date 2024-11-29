package com.meow_care.meow_care_service.dto.contract;

import lombok.Builder;

/**
 * DTO for {@link com.meow_care.meow_care_service.entities.ContractTemplate}
 */
@Builder
public record ContractTemplateRequestDto(
        String name,
        String content
) {
}