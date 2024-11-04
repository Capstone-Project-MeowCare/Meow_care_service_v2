package com.mewcare.meow_care_service.services.Impl;

import com.mewcare.meow_care_service.dto.WalletDto;
import com.mewcare.meow_care_service.entities.Wallet;
import com.mewcare.meow_care_service.mapper.WalletMapper;
import com.mewcare.meow_care_service.repositories.WalletRepository;
import com.mewcare.meow_care_service.services.WalletService;
import com.mewcare.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl extends BaseServiceImpl<WalletDto, Wallet, WalletRepository, WalletMapper>
        implements WalletService {
    public WalletServiceImpl(WalletRepository repository, WalletMapper mapper) {
        super(repository, mapper);
    }
}
