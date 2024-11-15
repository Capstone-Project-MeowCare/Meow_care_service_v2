package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.BookingOrderDto;
import com.meow_care.meow_care_service.dto.BookingOrderWithDetailDto;
import com.meow_care.meow_care_service.dto.MomoPaymentReturnDto;
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
import com.meow_care.meow_care_service.services.BookingOrderService;
import com.meow_care.meow_care_service.services.CareScheduleService;
import com.meow_care.meow_care_service.services.TransactionService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import com.meow_care.meow_care_service.util.UserUtils;
import com.mservice.config.Environment;
import com.mservice.enums.RequestType;
import com.mservice.models.PaymentResponse;
import com.mservice.processor.CreateOrderMoMo;
import com.mservice.shared.utils.Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class BookingOrderServiceImpl extends BaseServiceImpl<BookingOrderDto, BookingOrder, BookingOrderRepository, BookingOrderMapper>
        implements BookingOrderService {

    private static final Logger log = LoggerFactory.getLogger(BookingOrderServiceImpl.class);

    private final CareScheduleService careScheduleService;

    private final TransactionService transactionService;


    public BookingOrderServiceImpl(BookingOrderRepository repository, BookingOrderMapper mapper, CareScheduleService careScheduleService, TransactionService transactionService) {
        super(repository, mapper);
        this.careScheduleService = careScheduleService;
        this.transactionService = transactionService;
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
    public ApiResponse<PaymentResponse> createPaymentUrl(UUID id, RequestType requestType, String callBackUrl) throws Exception {

        Environment environment = Environment.selectEnv("dev");

        BookingOrder bookingOrder = repository.findById(id)
                .orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));

        if (bookingOrder.getStatus() != BookingOrderStatus.AWAITING_PAYMENT) {
            throw new ApiException(ApiStatus.PAYMENT_ERROR, "Booking order is not awaiting payment");
        }

        //sum price of services
        long total = (long) bookingOrder.getBookingDetails().stream().mapToDouble(detail -> detail.getService().getPrice() * detail.getQuantity()).sum();

        UUID transactionId = UUID.randomUUID();

        PaymentResponse paymentResponse = CreateOrderMoMo.process(environment, transactionId.toString(), UUID.randomUUID().toString(), Long.toString(total), "Pay With MoMo", "https://google.com.vn", callBackUrl, "", requestType, Boolean.TRUE);

        transactionService.create(Transaction.builder()
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

    @Override
    public ApiResponse<Long> countBookingOrderInTimeRange(Instant createdAtStart, Instant createdAtEnd) {
            long count = repository.countByCreatedAtBetween(createdAtStart, createdAtEnd);
            return ApiResponse.success(count);
    }

    @Override
    public ApiResponse<Long> countByStatus(BookingOrderStatus status) {
        long count = repository.countByStatus(status);
        return ApiResponse.success(count);
    }

    @Override
    public ApiResponse<Void> momoCallback(MomoPaymentReturnDto momoPaymentReturnDto) {
        Environment environment = Environment.selectEnv("dev");
        try {
            String signature = Encoder.signHmacSHA256(momoPaymentReturnDto.toMap(), environment);

            if (!signature.equals(momoPaymentReturnDto.signature())) {
                throw new ApiException(ApiStatus.SIGNATURE_NOT_MATCH, "Signature not match");
            }

            Transaction transaction = transactionService.findEntityById(UUID.fromString(momoPaymentReturnDto.orderId()));

            if (momoPaymentReturnDto.resultCode() == 0 || momoPaymentReturnDto.resultCode() == 9000) {
                // update transaction status to transfer money from user wallet to system wallet
                transaction.setStatus(TransactionStatus.COMPLETED);

                // update booking order status
                updateStatus(transaction.getBooking().getId(), BookingOrderStatus.AWAITING_CONFIRM);
            } else {
                transactionService.updateStatus(transaction.getId(), TransactionStatus.FAILED);
            }
            return ApiResponse.noBodyContent();
        } catch (Exception e) {
            log.error("Error while verifying signature", e);
            throw new ApiException(ApiStatus.ERROR, "Error while verifying signature");
        }
    }

}
