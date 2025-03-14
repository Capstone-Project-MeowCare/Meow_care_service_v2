package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.RefundDto;
import com.meow_care.meow_care_service.entities.Refund;
import com.meow_care.meow_care_service.mapper.RefundMapper;
import com.meow_care.meow_care_service.repositories.RefundRepository;
import com.meow_care.meow_care_service.services.RefundService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class RefundServiceImpl extends BaseServiceImpl<RefundDto, Refund, RefundRepository, RefundMapper>
        implements RefundService {
    public RefundServiceImpl(RefundRepository repository, RefundMapper mapper) {
        super(repository, mapper);
    }
}
