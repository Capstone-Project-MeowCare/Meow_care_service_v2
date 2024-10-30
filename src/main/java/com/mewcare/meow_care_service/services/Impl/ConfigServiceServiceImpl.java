package com.mewcare.meow_care_service.services.Impl;

import com.mewcare.meow_care_service.dto.ConfigServiceDto;
import com.mewcare.meow_care_service.entities.ConfigService;
import com.mewcare.meow_care_service.mapper.ConfigServiceMapper;
import com.mewcare.meow_care_service.repositories.ConfigServiceRepository;
import com.mewcare.meow_care_service.services.ConfigServiceService;
import com.mewcare.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceServiceImpl extends BaseServiceImpl<ConfigServiceDto, ConfigService, ConfigServiceRepository, ConfigServiceMapper>
        implements ConfigServiceService {
    public ConfigServiceServiceImpl(ConfigServiceRepository repository, ConfigServiceMapper mapper) {
        super(repository, mapper);
    }
}
