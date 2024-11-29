package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.ServiceDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.Service;
import com.meow_care.meow_care_service.entities.User;
import com.meow_care.meow_care_service.enums.ServiceStatus;
import com.meow_care.meow_care_service.enums.ServiceType;
import com.meow_care.meow_care_service.mapper.ServiceMapper;
import com.meow_care.meow_care_service.repositories.ServiceRepository;
import com.meow_care.meow_care_service.services.ServiceEntityService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import com.meow_care.meow_care_service.util.UserUtils;

import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Service
public class ServiceEntityServiceImpl extends BaseServiceImpl<ServiceDto, Service, ServiceRepository, ServiceMapper> implements ServiceEntityService {
    public ServiceEntityServiceImpl(ServiceRepository repository, ServiceMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public ApiResponse<ServiceDto> create(ServiceDto dto) {
        Service service = mapper.toEntity(dto);
        service.setSitter(User.builder().id(UserUtils.getCurrentUserId()).build());
        dto = mapper.toDto(repository.save(service));
        return ApiResponse.success(dto);
    }

    @Override
    public ApiResponse<List<ServiceDto>> getBySitterId(UUID id) {
        List<Service> services = repository.findBySitterId(id);
        return ApiResponse.success(mapper.toDtoList(services));
    }

    @Override
    public ApiResponse<List<ServiceDto>> getByServiceType(ServiceType serviceType) {
        List<Service> services = repository.findByServiceType(serviceType);
        return ApiResponse.success(mapper.toDtoList(services));
    }

    @Override
    public ApiResponse<List<ServiceDto>> getByServiceType(ServiceType serviceType, ServiceStatus status) {
        List<Service> services = repository.findByServiceTypeAndStatus(serviceType, status);
        return ApiResponse.success(mapper.toDtoList(services));
    }

    @Override
    public ApiResponse<List<ServiceDto>> getBySitterId(UUID id, ServiceType serviceType, ServiceStatus status) {
        List<Service> services = repository.findBySitterIdAndServiceTypeAndStatus(id, serviceType, status);
        return ApiResponse.success(mapper.toDtoList(services));
    }

}
