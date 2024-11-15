package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.AppSaveConfigDto;
import com.meow_care.meow_care_service.entities.AppSaveConfig;
import com.meow_care.meow_care_service.enums.ConfigKey;
import com.meow_care.meow_care_service.services.base.BaseService;

public interface AppSaveConfigService extends BaseService<AppSaveConfigDto, AppSaveConfig> {
    AppSaveConfig findByConfigKey(ConfigKey configKey);
}
