package com.mewcare.meow_care_service.services.Impl;

import com.mewcare.meow_care_service.dto.HistoryDto;
import com.mewcare.meow_care_service.entities.History;
import com.mewcare.meow_care_service.mapper.HistoryMapper;
import com.mewcare.meow_care_service.repositories.HistoryRepository;
import com.mewcare.meow_care_service.services.HistoryService;
import com.mewcare.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class HistoryServiceImpl extends BaseServiceImpl<HistoryDto, History, HistoryRepository, HistoryMapper>
        implements HistoryService {
    public HistoryServiceImpl(HistoryRepository repository, HistoryMapper mapper) {
        super(repository, mapper);
    }
}
