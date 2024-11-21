package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.ServiceDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.Service;
import com.meow_care.meow_care_service.entities.User;
import com.meow_care.meow_care_service.mapper.ServiceMapper;
import com.meow_care.meow_care_service.repositories.ServiceRepository;
import com.meow_care.meow_care_service.services.ServiceEntityService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import com.meow_care.meow_care_service.util.UserUtils;

import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Service
public class ServiceEntityServiceImpl extends BaseServiceImpl<ServiceDto, Service, ServiceRepository, ServiceMapper>
        implements ServiceEntityService {
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
    public ApiResponse<List<ServiceDto>> getByServiceTypeId(UUID id) {
        return ApiResponse.success(mapper.toDtoList(repository.findByConfigServiceServiceTypeId(id)));
    }

    @Override
    public ApiResponse<List<ServiceDto>> getBySitterId(UUID id) {
        List<Service> services = repository.findBySitterId(id);
        return ApiResponse.success(mapper.toDtoList(services));
    }

    @Override
    public ApiResponse<List<ServiceDto>> getBySitterId(UUID typeId, UUID id) {
        List<Service> services = repository.findBySitter_IdAndConfigService_ServiceType_Id(id, typeId);
        return ApiResponse.success(mapper.toDtoList(services));
    }
}
