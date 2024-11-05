package com.mewcare.meow_care_service.services.Impl;

import com.mewcare.meow_care_service.dto.BookingOrderDto;
import com.mewcare.meow_care_service.dto.BookingOrderWithDetailDto;
import com.mewcare.meow_care_service.dto.response.ApiResponse;
import com.mewcare.meow_care_service.entities.BookingOrder;
import com.mewcare.meow_care_service.entities.User;
import com.mewcare.meow_care_service.mapper.BookingOrderMapper;
import com.mewcare.meow_care_service.repositories.BookingOrderRepository;
import com.mewcare.meow_care_service.services.BookingOrderService;
import com.mewcare.meow_care_service.services.base.BaseServiceImpl;
import com.mewcare.meow_care_service.util.UserUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookingOrderServiceImpl extends BaseServiceImpl<BookingOrderDto, BookingOrder, BookingOrderRepository, BookingOrderMapper>
        implements BookingOrderService {
    public BookingOrderServiceImpl(BookingOrderRepository repository, BookingOrderMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public ApiResponse<BookingOrderDto> create(BookingOrderDto dto) {
        BookingOrder bookingOrder = mapper.toEntity(dto);
        bookingOrder.setPaymentStatus(0);
        bookingOrder.setStatus(0);
        bookingOrder.setUser(User.builder().id(UserUtils.getCurrentUserId()).build());
        bookingOrder = repository.save(bookingOrder);
        return ApiResponse.success(mapper.toDto(bookingOrder));
    }

    @Override
    public ApiResponse<BookingOrderWithDetailDto> createWithDetail(BookingOrderWithDetailDto dto) {
        BookingOrder bookingOrder = mapper.toEntityWithDetail(dto);
        bookingOrder.setPaymentStatus(0);
        bookingOrder.setStatus(0);
        bookingOrder.setUser(User.builder().id(UserUtils.getCurrentUserId()).build());
        bookingOrder = repository.save(bookingOrder);
        return ApiResponse.success(mapper.toDtoWithDetail(bookingOrder));
    }

    //get by user id
    @Override
    public ApiResponse<List<BookingOrderDto>> getByUserId(UUID id) {
        List<BookingOrder> bookingOrders = repository.findByUserId(id);
        return ApiResponse.success(mapper.toDtoList(bookingOrders));
    }

    //get by sitter id
    @Override
    public ApiResponse<List<BookingOrderDto>> getBySitterId(UUID id) {
        List<BookingOrder> bookingOrders = repository.findBySitterId(id);
        return ApiResponse.success(mapper.toDtoList(bookingOrders));
    }

}
