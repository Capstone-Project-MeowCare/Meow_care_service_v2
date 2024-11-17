package com.meow_care.meow_care_service.entities;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Notification {
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    private String userId;

    @Size(max = 100)
    private String title;

    private String message;

    @Size(max = 50)
    private String type;

    private UUID relatedId;

    @Size(max = 50)
    private String relatedType;

    @Builder.Default
    private Boolean isRead = false;

    @Builder.Default
    @Size(max = 20)
    private String status = "PENDING";

    @Builder.Default
    private Instant createdAt = Instant.now();

    @Builder.Default
    private Instant updatedAt = Instant.now();

}