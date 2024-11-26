package com.meow_care.meow_care_service.repositories.client;

import com.meow_care.meow_care_service.dto.response.NominatimResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "nominatimClient", url = "https://nominatim.openstreetmap.org")
public interface NominatimClient {

    @GetMapping(value = "/search", produces = "application/json")
    List<NominatimResponse> searchLocation(
            @RequestParam("q") String address,
            @RequestParam("format") String format
    );

}
