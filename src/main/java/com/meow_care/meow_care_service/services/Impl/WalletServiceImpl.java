package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.WalletDto;
import com.meow_care.meow_care_service.entities.Wallet;
import com.meow_care.meow_care_service.mapper.WalletMapper;
import com.meow_care.meow_care_service.repositories.WalletRepository;
import com.meow_care.meow_care_service.services.WalletService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl extends BaseServiceImpl<WalletDto, Wallet, WalletRepository, WalletMapper>
        implements WalletService {
    public WalletServiceImpl(WalletRepository repository, WalletMapper mapper) {
        super(repository, mapper);
    }
}
