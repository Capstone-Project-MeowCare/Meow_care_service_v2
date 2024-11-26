package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.response.NominatimResponse;
import com.meow_care.meow_care_service.repositories.client.NominatimClient;
import com.meow_care.meow_care_service.services.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final NominatimClient nominatimClient;

    @Override
    public List<NominatimResponse> getLocation(String address) {
        return List.of();
    }
}
