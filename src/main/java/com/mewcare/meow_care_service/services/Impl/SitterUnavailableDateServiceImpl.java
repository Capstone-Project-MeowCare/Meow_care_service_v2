package com.mewcare.meow_care_service.services.Impl;

import com.mewcare.meow_care_service.dto.SitterUnavailableDateDto;
import com.mewcare.meow_care_service.entities.SitterUnavailableDate;
import com.mewcare.meow_care_service.mapper.SitterUnavailableDateMapper;
import com.mewcare.meow_care_service.repositories.SitterUnavailableDateRepository;
import com.mewcare.meow_care_service.services.SitterUnavailableDateService;
import com.mewcare.meow_care_service.services.base.BaseServiceImpl;

public class SitterUnavailableDateServiceImpl extends BaseServiceImpl<SitterUnavailableDateDto, SitterUnavailableDate, SitterUnavailableDateRepository, SitterUnavailableDateMapper>
        implements SitterUnavailableDateService {
    public SitterUnavailableDateServiceImpl(SitterUnavailableDateRepository repository, SitterUnavailableDateMapper mapper) {
        super(repository, mapper);
    }
}
