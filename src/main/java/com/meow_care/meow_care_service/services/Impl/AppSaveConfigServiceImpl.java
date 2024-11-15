package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.AppSaveConfigDto;
import com.meow_care.meow_care_service.entities.AppSaveConfig;
import com.meow_care.meow_care_service.enums.ConfigKey;
import com.meow_care.meow_care_service.mapper.AppSaveConfigMapper;
import com.meow_care.meow_care_service.repositories.AppSaveConfigRepository;
import com.meow_care.meow_care_service.services.AppSaveConfigService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AppSaveConfigServiceImpl extends BaseServiceImpl<AppSaveConfigDto, AppSaveConfig, AppSaveConfigRepository, AppSaveConfigMapper> implements AppSaveConfigService {
    public AppSaveConfigServiceImpl(AppSaveConfigRepository repository, AppSaveConfigMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public AppSaveConfig findByConfigKey(ConfigKey configKey) {
        return repository.findByConfigKey(configKey).orElse(null);
    }
}
