package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.AppSaveConfigDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.AppSaveConfig;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.enums.ConfigKey;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.AppSaveConfigMapper;
import com.meow_care.meow_care_service.repositories.AppSaveConfigRepository;
import com.meow_care.meow_care_service.services.AppSaveConfigService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
public class AppSaveConfigServiceImpl extends BaseServiceImpl<AppSaveConfigDto, AppSaveConfig, AppSaveConfigRepository, AppSaveConfigMapper> implements AppSaveConfigService {

    public AppSaveConfigServiceImpl(AppSaveConfigRepository repository, AppSaveConfigMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public AppSaveConfig findByConfigKey(ConfigKey configKey) {
        return repository.findByConfigKey(configKey).orElse(null);
    }

    @Override
    public ApiResponse<AppSaveConfigDto> findByConfigKeyResponse(ConfigKey configKey) {
        AppSaveConfig appSaveConfig = repository.findByConfigKey(configKey).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND)
        );
        return ApiResponse.success(mapper.toDto(appSaveConfig));
    }

    @PostConstruct
    public void  init() {
        AppSaveConfig commissionAppConfig = repository.findByConfigKey(ConfigKey.APP_COMMISSION_SETTING).orElse(null);
        if (commissionAppConfig == null) {
            commissionAppConfig = AppSaveConfig.builder()
                    .configKey(ConfigKey.APP_COMMISSION_SETTING)
                    .configValue("0.10")
                    .description("Commission setting for the application")
                    .createdBy("system")
                    .createdAt(Instant.now())
                    .updatedBy("system")
                    .updatedAt(Instant.now())
                    .build();
            repository.save(commissionAppConfig);
            log.info("System init app config ");
        }
    }
}
