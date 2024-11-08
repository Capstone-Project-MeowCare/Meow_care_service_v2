package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.ChatRoom;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link ChatRoom}
 */
public record ChatRoomDto(
        UUID id,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Set<MessageDto> messages
) {
}