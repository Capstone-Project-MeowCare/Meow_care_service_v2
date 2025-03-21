package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.HolidayDto;
import com.meow_care.meow_care_service.entities.Holiday;
import com.meow_care.meow_care_service.mapper.HolidayMapper;
import com.meow_care.meow_care_service.repositories.HolidayRepository;
import com.meow_care.meow_care_service.services.HolidayService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class HolidayServiceImpl extends BaseServiceImpl<HolidayDto, Holiday, HolidayRepository, HolidayMapper>
        implements HolidayService {
    public HolidayServiceImpl(HolidayRepository repository, HolidayMapper mapper) {
        super(repository, mapper);
    }
}
