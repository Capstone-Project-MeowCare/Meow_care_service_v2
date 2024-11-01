package com.mewcare.meow_care_service.services.Impl;

import com.mewcare.meow_care_service.dto.HolidayDto;
import com.mewcare.meow_care_service.entities.Holiday;
import com.mewcare.meow_care_service.mapper.HolidayMapper;
import com.mewcare.meow_care_service.repositories.HolidayRepository;
import com.mewcare.meow_care_service.services.HolidayService;
import com.mewcare.meow_care_service.services.base.BaseServiceImpl;

public class HolidayServiceImpl extends BaseServiceImpl<HolidayDto, Holiday, HolidayRepository, HolidayMapper>
        implements HolidayService {
    public HolidayServiceImpl(HolidayRepository repository, HolidayMapper mapper) {
        super(repository, mapper);
    }
}
