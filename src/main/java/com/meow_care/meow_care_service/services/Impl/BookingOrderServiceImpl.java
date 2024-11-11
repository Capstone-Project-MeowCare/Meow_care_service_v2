package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.BookingOrderDto;
import com.meow_care.meow_care_service.dto.BookingOrderWithDetailDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.BookingOrder;
import com.meow_care.meow_care_service.entities.CareSchedule;
import com.meow_care.meow_care_service.entities.Transaction;
import com.meow_care.meow_care_service.entities.User;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.enums.BookingOrderStatus;
import com.meow_care.meow_care_service.enums.TransactionStatus;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.BookingOrderMapper;
import com.meow_care.meow_care_service.repositories.BookingOrderRepository;
import com.meow_care.meow_care_service.repositories.TransactionRepository;
import com.meow_care.meow_care_service.services.BookingOrderService;
import com.meow_care.meow_care_service.services.CareScheduleService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import com.meow_care.meow_care_service.util.UserUtils;
import com.mservice.config.Environment;
import com.mservice.enums.RequestType;
import com.mservice.models.PaymentResponse;
import com.mservice.processor.CreateOrderMoMo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class BookingOrderServiceImpl extends BaseServiceImpl<BookingOrderDto, BookingOrder, BookingOrderRepository, BookingOrderMapper>
        implements BookingOrderService {

    @Value("${momo.callback.url}")
    private String momoCallBackUrl;

    private static final Logger log = LoggerFactory.getLogger(BookingOrderServiceImpl.class);

    private final CareScheduleService careScheduleService;

    private final TransactionRepository transactionRepository;

    public BookingOrderServiceImpl(BookingOrderRepository repository, BookingOrderMapper mapper, CareScheduleService careScheduleService, TransactionRepository transactionRepository) {
        super(repository, mapper);
        this.careScheduleService = careScheduleService;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public ApiResponse<BookingOrderDto> create(BookingOrderDto dto) {
        BookingOrder bookingOrder = mapper.toEntity(dto);
        bookingOrder.setPaymentStatus(0);
        bookingOrder.setStatus(BookingOrderStatus.AWAITING_PAYMENT);
        bookingOrder.setUser(User.builder().id(UserUtils.getCurrentUserId()).build());
        bookingOrder = repository.save(bookingOrder);
        return ApiResponse.success(mapper.toDto(bookingOrder));
    }

    @Override
    public ApiResponse<BookingOrderWithDetailDto> getWithDetail(UUID id) {
        BookingOrder bookingOrder = repository.findById(id)
                .orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));
        return ApiResponse.success(mapper.toDtoWithDetail(bookingOrder));
    }

    @Override
    public ApiResponse<BookingOrderWithDetailDto> createWithDetail(BookingOrderWithDetailDto dto) {
        BookingOrder bookingOrder = mapper.toEntityWithDetail(dto);
        bookingOrder.setPaymentStatus(0);
        bookingOrder.setStatus(BookingOrderStatus.AWAITING_PAYMENT);
        bookingOrder.setUser(User.builder().id(UserUtils.getCurrentUserId()).build());
        bookingOrder = repository.save(bookingOrder);
        return ApiResponse.success(mapper.toDtoWithDetail(bookingOrder));
    }

    @Override
    public ApiResponse<List<BookingOrderWithDetailDto>> getByUserId(UUID id) {
        List<BookingOrder> bookingOrders = repository.findByUserId(id);
        return ApiResponse.success(mapper.toDtoWithDetail(bookingOrders));
    }

    @Override
    public ApiResponse<List<BookingOrderWithDetailDto>> getBySitterId(UUID id) {
        List<BookingOrder> bookingOrders = repository.findBySitterId(id);
        return ApiResponse.success(mapper.toDtoWithDetail(bookingOrders));
    }

    @Override
    public ApiResponse<Void> updateStatus(UUID id, BookingOrderStatus status) {
        if (!repository.existsById(id))
            throw new ApiException(ApiStatus.NOT_FOUND);

        int result = repository.updateStatusById(status, id);
        if (result == 0) {
            throw new ApiException(ApiStatus.UPDATE_ERROR);
        }

        if (status == BookingOrderStatus.CONFIRMED) {
            CareSchedule careSchedule = careScheduleService.createCareSchedule(id);
            log.info("CareSchedule created: {}", careSchedule);
        }

        return ApiResponse.updated();
    }

    @Override
    public ApiResponse<PaymentResponse> createPaymentUrl(UUID id, RequestType requestType) throws Exception {

        Environment environment = Environment.selectEnv("dev");

        BookingOrder bookingOrder = repository.findById(id)
                .orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));

        if (bookingOrder.getStatus() != BookingOrderStatus.AWAITING_PAYMENT) {
            throw new ApiException(ApiStatus.PAYMENT_ERROR, "Booking order is not awaiting payment");
        }

        //sum price of services
        long total = (long) bookingOrder.getBookingDetails().stream().mapToDouble(detail -> detail.getService().getPrice() * detail.getQuantity()).sum();

        UUID transactionId = UUID.randomUUID();

        PaymentResponse paymentResponse = CreateOrderMoMo.process(environment, transactionId.toString(), UUID.randomUUID().toString(), Long.toString(total), "Pay With MoMo", "https://google.com.vn", momoCallBackUrl, "", requestType, Boolean.TRUE);

        transactionRepository.save(Transaction.builder()
                .id(transactionId)
                .booking(bookingOrder)
                .amount(BigDecimal.valueOf(total))
                .paymentMethod("MOMO")
                .fromUser(bookingOrder.getUser())
                .toUser(bookingOrder.getSitter())
                .status(TransactionStatus.PENDING)
                .build());

        return ApiResponse.success(paymentResponse);
    }

}
