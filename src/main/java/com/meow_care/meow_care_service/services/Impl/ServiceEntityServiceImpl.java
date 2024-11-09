package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.ServiceDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.Service;
import com.meow_care.meow_care_service.mapper.ServiceMapper;
import com.meow_care.meow_care_service.repositories.ServiceRepository;
import com.meow_care.meow_care_service.services.ServiceEntityService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;

import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Service
public class ServiceEntityServiceImpl extends BaseServiceImpl<ServiceDto, Service, ServiceRepository, ServiceMapper>
        implements ServiceEntityService {
    public ServiceEntityServiceImpl(ServiceRepository repository, ServiceMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public ApiResponse<List<ServiceDto>> getByServiceTypeId(UUID id) {
        return ApiResponse.success(mapper.toDtoList(repository.findByConfigServiceServiceTypeId(id)));
    }

    @Override
    public ApiResponse<List<ServiceDto>> getBySitterId(UUID id) {
        List<Service> services = repository.findBySitterId(id);
        return ApiResponse.success(mapper.toDtoList(services));
    }
}
