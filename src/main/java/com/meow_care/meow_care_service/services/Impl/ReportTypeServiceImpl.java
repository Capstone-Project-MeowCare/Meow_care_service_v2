package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.ReportTypeDto;
import com.meow_care.meow_care_service.entities.ReportType;
import com.meow_care.meow_care_service.mapper.ReportTypeMapper;
import com.meow_care.meow_care_service.repositories.ReportTypeRepository;
import com.meow_care.meow_care_service.services.ReportTypeService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ReportTypeServiceImpl extends BaseServiceImpl<ReportTypeDto, ReportType, ReportTypeRepository, ReportTypeMapper> implements ReportTypeService {
    public ReportTypeServiceImpl(ReportTypeRepository repository, ReportTypeMapper mapper) {
        super(repository, mapper);
    }
}
