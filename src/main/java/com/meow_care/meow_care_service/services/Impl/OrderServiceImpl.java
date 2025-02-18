package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.OrderDto;
import com.meow_care.meow_care_service.entities.Order;
import com.meow_care.meow_care_service.mapper.OrderMapper;
import com.meow_care.meow_care_service.repositories.OrderRepository;
import com.meow_care.meow_care_service.services.OrderService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends BaseServiceImpl<OrderDto, Order, OrderRepository, OrderMapper>
        implements OrderService {
    public OrderServiceImpl(OrderRepository repository, OrderMapper mapper) {
        super(repository, mapper);
    }
}
