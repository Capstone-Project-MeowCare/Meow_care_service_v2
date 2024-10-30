package com.mewcare.meow_care_service.services.Impl;

import com.mewcare.meow_care_service.dto.ServiceDto;
import com.mewcare.meow_care_service.entities.Service;
import com.mewcare.meow_care_service.mapper.ServiceMapper;
import com.mewcare.meow_care_service.repositories.ServiceRepository;
import com.mewcare.meow_care_service.services.ServiceEntityService;
import com.mewcare.meow_care_service.services.base.BaseServiceImpl;

@org.springframework.stereotype.Service
public class ServiceEntityServiceImpl extends BaseServiceImpl<ServiceDto, Service, ServiceRepository, ServiceMapper>
        implements ServiceEntityService {
    public ServiceEntityServiceImpl(ServiceRepository repository, ServiceMapper mapper) {
        super(repository, mapper);
    }
}
