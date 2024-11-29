package com.meow_care.meow_care_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.meow_care.meow_care_service.entities.BookingDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link BookingDetail}
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
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

