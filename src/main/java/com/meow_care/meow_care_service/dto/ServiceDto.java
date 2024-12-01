package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.Service;
import com.meow_care.meow_care_service.enums.ServiceStatus;
import com.meow_care.meow_care_service.enums.ServiceType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

/**
 * DTO for {@link Service}
 */
@Getter
@Builder
public class ServiceDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    UUID id;

    String name;

    String actionDescription;

    @Builder.Default
    Integer price = 0;

    @Size(min = 1, max = 1440)
    Integer duration;

    @Size(max = 24)
    Integer startTime;

    @Size(max = 24)
    Integer endTime;

    ServiceType serviceType;

    ServiceStatus status;

    private boolean isDeleted;
}