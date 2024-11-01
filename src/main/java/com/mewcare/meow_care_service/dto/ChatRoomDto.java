package com.mewcare.meow_care_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link com.mewcare.meow_care_service.entities.ChatRoom}
 */
public record ChatRoomDto(UUID id,
                          @Schema(accessMode = Schema.AccessMode.READ_ONLY)
                          Set<MessageDto> messages) {
}