package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.ServiceDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.Service;
import com.meow_care.meow_care_service.services.base.BaseService;

import java.util.List;
import java.util.UUID;

public interface ServiceEntityService extends BaseService<ServiceDto, Service> {
    ApiResponse<List<ServiceDto>> getByServiceTypeId(UUID id);

    ApiResponse<List<ServiceDto>> getBySitterId(UUID id);
}
