package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.CareScheduleDto;
import com.meow_care.meow_care_service.entities.CareSchedule;
import com.meow_care.meow_care_service.mapper.CareScheduleMapper;
import com.meow_care.meow_care_service.repositories.CareScheduleRepository;
import com.meow_care.meow_care_service.services.CareScheduleService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CareScheduleServiceImpl extends BaseServiceImpl<CareScheduleDto, CareSchedule, CareScheduleRepository, CareScheduleMapper>
        implements CareScheduleService {
    public CareScheduleServiceImpl(CareScheduleRepository repository, CareScheduleMapper mapper) {
        super(repository, mapper);
    }
}
