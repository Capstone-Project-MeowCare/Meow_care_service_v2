package com.meow_care.meow_care_service.dto.contract;

import com.meow_care.meow_care_service.entities.Contract;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

/**
 * DTO for {@link Contract}
 */
@Getter
@Builder
public class ContractCreateRequestDto {

    UUID templateId;

    UUID userId;

    UUID sitterId;

    Map<String, String> data;
}