package com.mewcare.meow_care_service.services.Impl;

import com.mewcare.meow_care_service.dto.WalletHistoryDto;
import com.mewcare.meow_care_service.entities.WalletHistory;
import com.mewcare.meow_care_service.mapper.WalletHistoryMapper;
import com.mewcare.meow_care_service.repositories.WalletHistoryRepository;
import com.mewcare.meow_care_service.services.WalletHistoryService;
import com.mewcare.meow_care_service.services.base.BaseServiceImpl;

public class WalletHistoryServiceImpl extends BaseServiceImpl<WalletHistoryDto, WalletHistory, WalletHistoryRepository, WalletHistoryMapper>
        implements WalletHistoryService {
    public WalletHistoryServiceImpl(WalletHistoryRepository repository, WalletHistoryMapper mapper) {
        super(repository, mapper);
    }
}
