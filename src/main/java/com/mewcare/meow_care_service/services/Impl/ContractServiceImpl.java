package com.mewcare.meow_care_service.services.Impl;

import com.mewcare.meow_care_service.dto.ContractDto;
import com.mewcare.meow_care_service.entities.Contract;
import com.mewcare.meow_care_service.mapper.ContractMapper;
import com.mewcare.meow_care_service.repositories.ContractRepository;
import com.mewcare.meow_care_service.services.ContractService;
import com.mewcare.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ContractServiceImpl extends BaseServiceImpl<ContractDto, Contract, ContractRepository, ContractMapper>
        implements ContractService {
    public ContractServiceImpl(ContractRepository repository, ContractMapper mapper) {
        super(repository, mapper);
    }
}
