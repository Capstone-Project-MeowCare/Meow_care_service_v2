package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.ServiceDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.Service;
import com.meow_care.meow_care_service.entities.SitterProfile;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.enums.ServiceStatus;
import com.meow_care.meow_care_service.enums.ServiceType;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.ServiceMapper;
import com.meow_care.meow_care_service.repositories.ServiceRepository;
import com.meow_care.meow_care_service.services.ServiceEntityService;
import com.meow_care.meow_care_service.services.SitterProfileService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import com.meow_care.meow_care_service.util.UserUtils;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Service
public class ServiceEntityServiceImpl extends BaseServiceImpl<ServiceDto, Service, ServiceRepository, ServiceMapper> implements ServiceEntityService {

    private final SitterProfileService sitterProfileService;

    public ServiceEntityServiceImpl(ServiceRepository repository, ServiceMapper mapper, SitterProfileService sitterProfileService) {
        super(repository, mapper);
        this.sitterProfileService = sitterProfileService;
    }

    @Override
    public ApiResponse<ServiceDto> create(ServiceDto dto) {
        Service service = mapper.toEntity(dto);
        SitterProfile sitterProfile = sitterProfileService.getEntityByUserId(UserUtils.getCurrentUserId())
                .orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND, "Sitter profile not found"));
        service.setSitterProfile(sitterProfile);
        service.setStatus(ServiceStatus.ACTIVE);

        //check service type if child service start time and end time must be not null
        switch (service.getServiceType()) {
            case CHILD_SERVICE:
                if (service.getStartTime() == null || service.getEndTime() == null) {
                    throw new ApiException(ApiStatus.INVALID_REQUEST, "Start time and end time must be not null");
                }
                service.setPrice(0);
                break;

            //if addition service, set must have duration
            case ADDITION_SERVICE:
                break;

            default:
                break;
        }


        dto = mapper.toDto(repository.save(service));
        return ApiResponse.success(dto);
    }

    @Override
    public ApiResponse<List<ServiceDto>> getBySitterId(UUID id) {
        List<Service> services = repository.findBySitterProfile_User_Id(id);
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
    public ApiResponse<List<ServiceDto>> getBySitterId(UUID id, ServiceType serviceType, @Nullable ServiceStatus status) {
        List<Service> services;
        if (status == null) {
            services = repository.findBySitterProfile_User_IdAndServiceType(id, serviceType);
        } else {
            services = repository.findBySitterProfile_User_IdAndServiceTypeAndStatus(id, serviceType, status);
        }
        return ApiResponse.success(mapper.toDtoList(services));
    }

    @Override
    @Transactional
    public ApiResponse<Void> softDeleteService(UUID serviceId) {
        Service service = repository.findById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("Service not found"));
        service.setIsDeleted(true);
        service.setStatus(ServiceStatus.DELETED);
        repository.save(service);
        return ApiResponse.deleted();
    }

    @Override
    public ApiResponse<Void> updateStatusById(ServiceStatus status, UUID id) {
        if (repository.updateStatusById(status, id) == 0) {
            throw new ApiException(ApiStatus.UPDATE_ERROR, "Service update status failed");
        }
        return ApiResponse.updated();
    }

}
