package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.AppSaveConfigDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.enums.ConfigKey;
import com.meow_care.meow_care_service.services.AppSaveConfigService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/config")
public class AppSaveConfigController {

    private final AppSaveConfigService appSaveConfigService;

    @Autowired
    public AppSaveConfigController(AppSaveConfigService appSaveConfigService) {
        this.appSaveConfigService = appSaveConfigService;
    }

    @GetMapping
    public ApiResponse<List<AppSaveConfigDto>> getAllConfigs() {
        return appSaveConfigService.getAll();
    }

    @GetMapping("/{configKey}")
    public ApiResponse<AppSaveConfigDto> getConfigByKey(@PathVariable ConfigKey configKey) {
        return appSaveConfigService.findByConfigKeyResponse(configKey);
    }

    @PostMapping
    public ApiResponse<AppSaveConfigDto> createConfig(@RequestBody AppSaveConfigDto appSaveConfigDto) {
        return appSaveConfigService.create(appSaveConfigDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<AppSaveConfigDto> updateConfig(@PathVariable UUID id, @RequestBody AppSaveConfigDto appSaveConfigDto) {
        return appSaveConfigService.update(id, appSaveConfigDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteConfig(@PathVariable UUID id) {
        return appSaveConfigService.delete(id);
    }
}
