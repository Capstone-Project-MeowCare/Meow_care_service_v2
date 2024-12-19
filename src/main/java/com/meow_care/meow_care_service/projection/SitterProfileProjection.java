package com.meow_care.meow_care_service.projection;

import java.util.UUID;

/**
 * Projection for {@link com.meow_care.meow_care_service.entities.SitterProfile}
 */
public interface SitterProfileProjection {
    UUID getId();

    Double getLatitude();

    Double getLongitude();

}