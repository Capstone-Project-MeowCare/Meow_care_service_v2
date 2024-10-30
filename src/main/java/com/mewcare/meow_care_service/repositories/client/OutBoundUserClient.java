package com.mewcare.meow_care_service.repositories.client;


import com.mewcare.meow_care_service.dto.response.OutBoundUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "outbound-user-service", url = "${google.user-service.url}")
public interface OutBoundUserClient {

    @GetMapping(value = "/oauth2/v1/userinfo")
    OutBoundUserResponse getUserInfo(
            @RequestParam("alt") String alt,
            @RequestParam("access_token") String accessToken
    );

}
