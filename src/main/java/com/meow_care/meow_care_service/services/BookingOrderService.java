package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.MomoPaymentReturnDto;
import com.meow_care.meow_care_service.dto.booking_order.BookingOrderDto;
import com.meow_care.meow_care_service.dto.booking_order.BookingOrderRequest;
import com.meow_care.meow_care_service.dto.booking_order.BookingOrderWithDetailDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.BookingOrder;
import com.meow_care.meow_care_service.enums.BookingOrderStatus;
import com.meow_care.meow_care_service.enums.OrderType;
import com.meow_care.meow_care_service.services.base.BaseService;
import com.mservice.enums.RequestType;
import com.mservice.models.PaymentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface BookingOrderService extends BaseService<BookingOrderDto, BookingOrder> {

    ApiResponse<BookingOrderWithDetailDto> getWithDetail(UUID id);

    ApiResponse<Boolean> isFullSlot(UUID sitterId, Instant startDate, Instant endDate, Integer numberOfPets);

    ApiResponse<BookingOrderWithDetailDto> createWithDetail(BookingOrderRequest dto);

    ApiResponse<Page<BookingOrderDto>> getAll(int page, int size, String prop, Sort.Direction direction);

    ApiResponse<List<BookingOrderWithDetailDto>> getByUserId(UUID id);

    ApiResponse<List<BookingOrderWithDetailDto>> getBySitterId(UUID id);

    ApiResponse<Page<BookingOrderWithDetailDto>> getByUserId(UUID id, int page, int size, String prop, Sort.Direction direction);

    ApiResponse<Page<BookingOrderWithDetailDto>> getBySitterId(UUID id, int page, int size, String prop, Sort.Direction direction);

    ApiResponse<Page<BookingOrderWithDetailDto>> getByUserIdAndStatus(UUID userId, BookingOrderStatus status, int page, int size, String prop, Sort.Direction direction);

    ApiResponse<Page<BookingOrderWithDetailDto>> getBySitterIdAndStatus(UUID sitterId, BookingOrderStatus status, int page, int size, String prop, Sort.Direction direction);

    ApiResponse<Void> updateStatus(UUID id, BookingOrderStatus status);

    ApiResponse<PaymentResponse> createPaymentUrl(UUID id, RequestType requestType, String redirectUrl) throws Exception;

    ApiResponse<Long> countBookingOrderInTimeRange(Instant createdAtStart, Instant createdAtEnd);

    ApiResponse<Long> countByStatusAndUpdatedAtBetween(BookingOrderStatus status, Instant from, Instant to);

    ApiResponse<Long> countByStatus(BookingOrderStatus status);

    //count by user id, status, order type
    ApiResponse<Long> countByUserIdAndStatusAndOrderType(UUID sitterId, @Nullable BookingOrderStatus status, @Nullable OrderType orderType);

    //count by sitter id and status, order type
    ApiResponse<Long> countBySitterIdAndStatusAndOrderType(UUID id, @Nullable BookingOrderStatus status, @Nullable OrderType orderType);

    ApiResponse<Void> momoCallback(MomoPaymentReturnDto momoPaymentReturnDto);

    //get total price of booking order id
    ApiResponse<BigDecimal> getTotalPrice(UUID id);

}
