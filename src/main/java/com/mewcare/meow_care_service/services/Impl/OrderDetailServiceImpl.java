package com.mewcare.meow_care_service.services.Impl;

import com.mewcare.meow_care_service.dto.OrderDetailDto;
import com.mewcare.meow_care_service.entities.OrderDetail;
import com.mewcare.meow_care_service.mapper.OrderDetailMapper;
import com.mewcare.meow_care_service.repositories.OrderDetailRepository;
import com.mewcare.meow_care_service.services.OrderDetailService;
import com.mewcare.meow_care_service.services.base.BaseServiceImpl;

public class OrderDetailServiceImpl extends BaseServiceImpl<OrderDetailDto, OrderDetail, OrderDetailRepository, OrderDetailMapper>
        implements OrderDetailService {
    public OrderDetailServiceImpl(OrderDetailRepository repository, OrderDetailMapper mapper) {
        super(repository, mapper);
    }
}
