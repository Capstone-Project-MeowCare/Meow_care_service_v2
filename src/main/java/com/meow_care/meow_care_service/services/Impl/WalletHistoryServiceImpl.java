package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.WalletHistoryDto;
import com.meow_care.meow_care_service.entities.WalletHistory;
import com.meow_care.meow_care_service.mapper.WalletHistoryMapper;
import com.meow_care.meow_care_service.repositories.WalletHistoryRepository;
import com.meow_care.meow_care_service.services.WalletHistoryService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class WalletHistoryServiceImpl extends BaseServiceImpl<WalletHistoryDto, WalletHistory, WalletHistoryRepository, WalletHistoryMapper>
        implements WalletHistoryService {
    public WalletHistoryServiceImpl(WalletHistoryRepository repository, WalletHistoryMapper mapper) {
        super(repository, mapper);
    }
}
