package com.mewcare.meow_care_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mewcare.meow_care_service.entities.BookingDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link BookingDetail}
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(accessMode = Schema.AccessMode.READ_ONLY)
@Builder
public record BookingDetailWithPetAndServiceDto(
        UUID id,
        @NotNull Integer quantity,
        Integer status,
        Instant createdAt,
        Instant updatedAt,
        PetProfileDto pet,
        ServiceDto service,
        @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
        UUID petProfileId,
        @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
        UUID serviceId
) {
}
