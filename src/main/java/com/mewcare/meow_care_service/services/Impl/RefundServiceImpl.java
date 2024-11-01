package com.mewcare.meow_care_service.services.Impl;

import com.mewcare.meow_care_service.dto.RefundDto;
import com.mewcare.meow_care_service.entities.Refund;
import com.mewcare.meow_care_service.mapper.RefundMapper;
import com.mewcare.meow_care_service.repositories.RefundRepository;
import com.mewcare.meow_care_service.services.RefundService;
import com.mewcare.meow_care_service.services.base.BaseServiceImpl;

public class RefundServiceImpl extends BaseServiceImpl<RefundDto, Refund, RefundRepository, RefundMapper>
        implements RefundService {
    public RefundServiceImpl(RefundRepository repository, RefundMapper mapper) {
        super(repository, mapper);
    }
}
