package com.mewcare.meow_care_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mewcare.meow_care_service.entities.BookingDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link BookingDetail}
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BookingDetailDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        UUID id,
        @NotNull Integer quantity,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Integer status,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Instant createdAt,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Instant updatedAt,
        @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
        UUID petProfileId,
        @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
        UUID serviceId
) {
}