package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.HistoryDto;
import com.meow_care.meow_care_service.entities.History;
import com.meow_care.meow_care_service.mapper.HistoryMapper;
import com.meow_care.meow_care_service.repositories.HistoryRepository;
import com.meow_care.meow_care_service.services.HistoryService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class HistoryServiceImpl extends BaseServiceImpl<HistoryDto, History, HistoryRepository, HistoryMapper>
        implements HistoryService {
    public HistoryServiceImpl(HistoryRepository repository, HistoryMapper mapper) {
        super(repository, mapper);
    }
}
