package com.mewcare.meow_care_service.repositories.client;


import com.mewcare.meow_care_service.dto.request.ExchangeTokenRequest;
import com.mewcare.meow_care_service.dto.response.ExchangeTokenResponse;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "identity-service", url = "${google.identity-service.url}")
// "identity-service" -> "identity-service
public interface OutBoundIdentityClient {

    @PostMapping(value = "/token", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ExchangeTokenResponse exchangeToken(@QueryMap ExchangeTokenRequest exchangeTokenRequest);

}
