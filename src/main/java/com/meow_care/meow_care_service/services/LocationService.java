package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.response.NominatimResponse;

import java.util.List;

public interface LocationService {
    List<NominatimResponse> getLocation(String address);
}
