package com.meow_care.meow_care_service.projection;

import com.meow_care.meow_care_service.entities.SitterProfile;
import com.meow_care.meow_care_service.enums.SitterProfileStatus;

import java.time.Instant;
import java.util.UUID;

/**
 * Projection for {@link SitterProfile}
 */
public interface SitterProfileInfo {
    UUID getId();

    String getBio();

    String getExperience();

    String getSkill();

    Double getRating();

    String getLocation();

    Double getLatitude();

    Double getLongitude();

    String getEnvironment();

    Integer getMaximumQuantity();

    SitterProfileStatus getStatus();

    Instant getCreatedAt();

    Instant getUpdatedAt();

    double getDistance();

    UUID getUserId();

    String getFullName();

    String getAvatar();

    Integer getFullRefundDay();

    Integer getMainServicePrice();
}