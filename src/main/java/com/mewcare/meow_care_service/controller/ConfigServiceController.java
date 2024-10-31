package com.mewcare.meow_care_service.controller;

import com.mewcare.meow_care_service.dto.ConfigServiceDto;
import com.mewcare.meow_care_service.dto.response.ApiResponse;
import com.mewcare.meow_care_service.services.ConfigServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/config-services")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class ConfigServiceController {

    private final ConfigServiceService configServiceService;

    @GetMapping("/{id}")
    public ApiResponse<ConfigServiceDto> getConfigServiceById(@PathVariable UUID id) {
        return configServiceService.get(id);
    }

    @PostMapping
    public ApiResponse<ConfigServiceDto> createConfigService(@RequestBody ConfigServiceDto configServiceDto) {
        return configServiceService.create(configServiceDto);
    }

    @GetMapping
    public ApiResponse<List<ConfigServiceDto>> getAllConfigServices() {
        return configServiceService.getAll();
    }

    @PutMapping("/{id}")
    public ApiResponse<ConfigServiceDto> updateConfigService(@PathVariable UUID id, @RequestBody ConfigServiceDto configServiceDto) {
        return configServiceService.update(id, configServiceDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteConfigService(@PathVariable UUID id) {
        return configServiceService.delete(id);
    }

}
