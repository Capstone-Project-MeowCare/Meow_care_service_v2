package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.ServiceTypeDto;
import com.meow_care.meow_care_service.entities.ServiceType;
import com.meow_care.meow_care_service.mapper.ServiceTypeMapper;
import com.meow_care.meow_care_service.repositories.ServiceTypeRepository;
import com.meow_care.meow_care_service.services.ServiceTypeService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ServiceTypeServiceImpl extends BaseServiceImpl<ServiceTypeDto, ServiceType, ServiceTypeRepository, ServiceTypeMapper>
        implements ServiceTypeService {
    public ServiceTypeServiceImpl(ServiceTypeRepository repository, ServiceTypeMapper mapper) {
        super(repository, mapper);
    }
}
