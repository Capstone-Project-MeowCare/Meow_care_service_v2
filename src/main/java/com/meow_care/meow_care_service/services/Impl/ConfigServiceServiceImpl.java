package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.ConfigServiceDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.ConfigService;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.ConfigServiceMapper;
import com.meow_care.meow_care_service.repositories.ConfigServiceRepository;
import com.meow_care.meow_care_service.repositories.ServiceRepository;
import com.meow_care.meow_care_service.services.ConfigServiceService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ConfigServiceServiceImpl extends BaseServiceImpl<ConfigServiceDto, ConfigService, ConfigServiceRepository, ConfigServiceMapper> implements ConfigServiceService {

    private final ServiceRepository serviceRepository;

    public ConfigServiceServiceImpl(ConfigServiceRepository repository, ConfigServiceMapper mapper, ServiceRepository serviceRepository) {
        super(repository, mapper);
        this.serviceRepository = serviceRepository;
    }

    @Override
    @Transactional
    public ApiResponse<ConfigServiceDto> update(UUID id, ConfigServiceDto dto) {

        ConfigService configService = repository.findById(id).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Config service not found")
        );

        String oldName = configService.getName();

        mapper.partialUpdate(dto, configService);

        configService = repository.save(configService);

        if (dto.name() != null && !oldName.equals(dto.name())) {
            serviceRepository.updateNameByName(dto.name(), oldName);
        }

        return ApiResponse.success(mapper.toDto(configService));
    }
}
