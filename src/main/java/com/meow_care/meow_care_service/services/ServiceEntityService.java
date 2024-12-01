package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.ServiceDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.Service;
import com.meow_care.meow_care_service.enums.ServiceStatus;
import com.meow_care.meow_care_service.enums.ServiceType;
import com.meow_care.meow_care_service.services.base.BaseService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface ServiceEntityService extends BaseService<ServiceDto, Service> {
    ApiResponse<List<ServiceDto>> getBySitterId(UUID id);

    ApiResponse<List<ServiceDto>> getByServiceType(ServiceType serviceType);

    ApiResponse<List<ServiceDto>> getByServiceType(ServiceType serviceType, ServiceStatus status);

    ApiResponse<List<ServiceDto>> getBySitterId(UUID id, ServiceType serviceType, ServiceStatus status);

    @Transactional
    ApiResponse<Void> softDeleteService(UUID serviceId);
}
