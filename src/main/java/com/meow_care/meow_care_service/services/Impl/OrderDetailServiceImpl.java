package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.OrderDetailDto;
import com.meow_care.meow_care_service.entities.OrderDetail;
import com.meow_care.meow_care_service.mapper.OrderDetailMapper;
import com.meow_care.meow_care_service.repositories.OrderDetailRepository;
import com.meow_care.meow_care_service.services.OrderDetailService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends BaseServiceImpl<OrderDetailDto, OrderDetail, OrderDetailRepository, OrderDetailMapper>
        implements OrderDetailService {
    public OrderDetailServiceImpl(OrderDetailRepository repository, OrderDetailMapper mapper) {
        super(repository, mapper);
    }
}
