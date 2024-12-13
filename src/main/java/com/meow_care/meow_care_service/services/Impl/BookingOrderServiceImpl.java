package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.MomoPaymentReturnDto;
import com.meow_care.meow_care_service.dto.booking_order.BookingOrderDto;
import com.meow_care.meow_care_service.dto.booking_order.BookingOrderRequest;
import com.meow_care.meow_care_service.dto.booking_order.BookingOrderWithDetailDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.AppSaveConfig;
import com.meow_care.meow_care_service.entities.BookingDetail;
import com.meow_care.meow_care_service.entities.BookingOrder;
import com.meow_care.meow_care_service.entities.Transaction;
import com.meow_care.meow_care_service.entities.User;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.enums.BookingOrderStatus;
import com.meow_care.meow_care_service.enums.BookingSlotStatus;
import com.meow_care.meow_care_service.enums.ConfigKey;
import com.meow_care.meow_care_service.enums.OrderType;
import com.meow_care.meow_care_service.enums.PaymentMethod;
import com.meow_care.meow_care_service.enums.ServiceStatus;
import com.meow_care.meow_care_service.enums.ServiceType;
import com.meow_care.meow_care_service.enums.TransactionStatus;
import com.meow_care.meow_care_service.enums.TransactionType;
import com.meow_care.meow_care_service.event.NotificationEvent;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.BookingOrderMapper;
import com.meow_care.meow_care_service.repositories.BookingOrderRepository;
import com.meow_care.meow_care_service.services.AppSaveConfigService;
import com.meow_care.meow_care_service.services.BookingOrderService;
import com.meow_care.meow_care_service.services.BookingSlotService;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class BookingOrderServiceImpl extends BaseServiceImpl<BookingOrderDto, BookingOrder, BookingOrderRepository, BookingOrderMapper> implements BookingOrderService {

    @Value("${momo.callback.order.url}")
    private String momoCallbackUrl;

    private static final Logger log = LoggerFactory.getLogger(BookingOrderServiceImpl.class);

    private final CareScheduleService careScheduleService;

    private final TransactionService transactionService;

    private final AppSaveConfigService appSaveConfigService;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final BookingSlotService bookingSlotService;

    Environment environment = Environment.selectEnv("dev");

    public BookingOrderServiceImpl(BookingOrderRepository repository, BookingOrderMapper mapper, CareScheduleService careScheduleService, TransactionService transactionService, AppSaveConfigService appSaveConfigService, ApplicationEventPublisher applicationEventPublisher, BookingSlotService bookingSlotService) {
        super(repository, mapper);
        this.careScheduleService = careScheduleService;
        this.transactionService = transactionService;
        this.appSaveConfigService = appSaveConfigService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.bookingSlotService = bookingSlotService;
    }

    @Override
    public ApiResponse<BookingOrderDto> create(BookingOrderDto dto) {
        BookingOrder bookingOrder = mapper.toEntity(dto);
        bookingOrder.setPaymentStatus(0);
        bookingOrder.setStatus(BookingOrderStatus.AWAITING_PAYMENT);
        bookingOrder.setUser(User.builder().id(UserUtils.getCurrentUserId()).build());

        if (dto.paymentMethod() == PaymentMethod.PAY_LATER) {
            bookingOrder.setStatus(BookingOrderStatus.CONFIRMED);
        }

        bookingOrder = repository.save(bookingOrder);

        handleStatusUpdate(bookingOrder.getId(), bookingOrder.getStatus());

        return ApiResponse.success(mapper.toDto(bookingOrder));
    }

    @Override
    public ApiResponse<BookingOrderWithDetailDto> createWithDetail(BookingOrderRequest dto) {

        if (dto.bookingDetails().isEmpty()) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Booking details is empty");
        }

        BookingOrder bookingOrder = mapper.toEntityWithDetail(dto);

        //if booking detail type is addition service must have slot id
        bookingOrder.getBookingDetails().forEach(detail -> {
            if (detail.getService().getServiceType() == ServiceType.ADDITION_SERVICE && detail.getBookingSlotId() == null) {
                throw new ApiException(ApiStatus.INVALID_REQUEST, "Slot id is required for addition service");
            }
        });


        bookingOrder.setPaymentStatus(0);
        bookingOrder.setStatus(BookingOrderStatus.AWAITING_PAYMENT);
        bookingOrder.setUser(User.builder().id(UserUtils.getCurrentUserId()).build());

        if (dto.paymentMethod() == PaymentMethod.PAY_LATER) {
            bookingOrder.setStatus(BookingOrderStatus.CONFIRMED);
        }

        bookingOrder = repository.save(bookingOrder);

        bookingOrder = repository.save(bookingOrder);
        return ApiResponse.success(mapper.toDtoWithDetail(bookingOrder));
    }

    @Override
    public ApiResponse<BookingOrderWithDetailDto> getWithDetail(UUID id) {
        BookingOrder bookingOrder = repository.findById(id)
                .orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));
        return ApiResponse.success(mapper.toDtoWithDetail(bookingOrder));
    }

    @Override
    public ApiResponse<Page<BookingOrderDto>> getAll(int page, int size, String prop, Sort.Direction direction) {
        Page<BookingOrder> bookingOrders = repository.findAll(PageRequest.of(page, size, Sort.by(direction, prop)));
        return ApiResponse.success(bookingOrders.map(mapper::toDto));
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
    public ApiResponse<Page<BookingOrderWithDetailDto>> getByUserId(UUID id, int page, int size, String prop, Sort.Direction direction) {
        Page<BookingOrder> bookingOrders = repository.findByUser_Id(id, BookingOrderStatus.AWAITING_CONFIRM, PageRequest.of(page, size, Sort.by(direction, prop)));
        return ApiResponse.success(bookingOrders.map(mapper::toDtoWithDetail));
    }

    @Override
    public ApiResponse<Page<BookingOrderWithDetailDto>> getBySitterId(UUID id, int page, int size, String prop, Sort.Direction direction) {
        Page<BookingOrder> bookingOrders = repository.findBySitter_Id(id, BookingOrderStatus.AWAITING_PAYMENT, PageRequest.of(page, size, Sort.by(direction, prop)));
        return ApiResponse.success(bookingOrders.map(mapper::toDtoWithDetail));
    }

    @Override
    public ApiResponse<Page<BookingOrderWithDetailDto>> getByUserIdAndStatus(UUID userId, BookingOrderStatus status, int page, int size, String prop, Sort.Direction direction) {
        Page<BookingOrder> bookingOrders = repository.findByUser_IdAndOptionalStatus(
                userId,
                status,
                PageRequest.of(page, size, Sort.by(direction, prop))
        );
        return ApiResponse.success(bookingOrders.map(mapper::toDtoWithDetail));
    }

    @Override
    public ApiResponse<Page<BookingOrderWithDetailDto>> getBySitterIdAndStatus(UUID sitterId, BookingOrderStatus status, int page, int size, String prop, Sort.Direction direction) {
        Page<BookingOrder> bookingOrders = repository.findBySitter_IdAndOptionalStatus(
                sitterId,
                status,
                PageRequest.of(page, size, Sort.by(direction, prop))
        );
        return ApiResponse.success(bookingOrders.map(mapper::toDtoWithDetail));
    }

    @Override
    public ApiResponse<Void> updateStatus(UUID id, BookingOrderStatus status) {

        if (repository.updateStatusById(status, id) == 0) {
            if (!repository.existsById(id))
                throw new ApiException(ApiStatus.NOT_FOUND);
            throw new ApiException(ApiStatus.UPDATE_ERROR);
        }

        handleStatusUpdate(id, status);

        return ApiResponse.updated();
    }

    @Override
    public ApiResponse<PaymentResponse> createPaymentUrl(UUID id, RequestType requestType, String redirectUrl) {


        BookingOrder bookingOrder = repository.findById(id)
                .orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));

        if (bookingOrder.getStatus() != BookingOrderStatus.AWAITING_PAYMENT) {
            throw new ApiException(ApiStatus.PAYMENT_ERROR, "Booking order is not awaiting payment");
        }

        //sum price of services
        long total = calculateTotalBookingPrice(bookingOrder).longValue();

        UUID transactionId = UUID.randomUUID();

        PaymentResponse paymentResponse;
        try {
            paymentResponse = CreateOrderMoMo.process(environment, transactionId.toString(), UUID.randomUUID().toString(), Long.toString(total), "Pay With MoMo", redirectUrl, momoCallbackUrl, "", requestType, Boolean.TRUE);
        } catch (Exception e) {
            log.error("Error while creating payment url", e);
            throw new ApiException(ApiStatus.ERROR, "Error while creating payment url");
        }

        transactionService.create(Transaction.builder()
                .id(transactionId)
                .booking(bookingOrder)
                .amount(BigDecimal.valueOf(total))
                .paymentMethod(PaymentMethod.MOMO)
                .transactionType(TransactionType.PAYMENT)
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
    @Transactional
    public ApiResponse<Void> momoCallback(MomoPaymentReturnDto momoPaymentReturnDto) {
        log.info("Momo callback: {}", momoPaymentReturnDto);

        Environment environment = Environment.selectEnv("dev");
        try {
            String signature = Encoder.signHmacSHA256(momoPaymentReturnDto.toMap(), environment);

            if (!signature.equals(momoPaymentReturnDto.signature())) {
                throw new ApiException(ApiStatus.SIGNATURE_NOT_MATCH, "Signature not match");
            }

            UUID transactionId = UUID.fromString(momoPaymentReturnDto.orderId());

            if (momoPaymentReturnDto.resultCode() == 0) {
                // update transaction status to transfer money from user wallet to system wallet
                transactionService.updateTransactionToHolding(transactionId, momoPaymentReturnDto.transId());

                BookingOrder bookingOrder = repository.findFirstByTransactionsId(transactionId).orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND, "Booking order not found"));

                // update booking order status
                updateStatus(bookingOrder.getId(), BookingOrderStatus.CONFIRMED);
            } else {
                transactionService.updateStatus(transactionId, TransactionStatus.FAILED);
            }
            return ApiResponse.noBodyContent();
        } catch (Exception e) {
            log.error("Error while verifying signature", e);
            throw new ApiException(ApiStatus.ERROR, "Error while verifying signature");
        }
    }

    //get total price of booking order id
    @Override
    public ApiResponse<BigDecimal> getTotalPrice(UUID id) {
        BookingOrder bookingOrder = repository.findById(id)
                .orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));
        return ApiResponse.success(calculateTotalBookingPrice(bookingOrder));
    }

    private BigDecimal calculateTotalBookingPrice(BookingOrder bookingOrder) {
        final long days = bookingOrder.getOrderType() == OrderType.OVERNIGHT
                ? bookingOrder.getStartDate().until(bookingOrder.getEndDate(), ChronoUnit.DAYS) + 1
                : 1;

        //remove is delete
        List<BookingDetail> bookingDetails = bookingOrder.getBookingDetails().stream().filter(detail -> detail.getService().getStatus().equals(ServiceStatus.ACTIVE)).toList();
        return bookingDetails.stream()
                .map(detail -> BigDecimal.valueOf((long) detail.getService().getPrice() * detail.getQuantity() * days))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void handleStatusUpdate(UUID id, BookingOrderStatus status) {
        BookingOrder bookingOrder;

        switch (status) {
            case AWAITING_CONFIRM -> {
                bookingOrder = repository.getReferenceById(id);
                applicationEventPublisher.publishEvent(new NotificationEvent(this, bookingOrder.getSitter().getId(),
                        "Bạn có một đơn đặt lịch mới.",
                        "Một đơn đặt lịch mới từ " + bookingOrder.getUser().getFullName() + " đang chờ bạn xác nhận."));
            }
            case CONFIRMED -> {
                bookingOrder = repository.getReferenceById(id);
                switch (bookingOrder.getOrderType()) {
                    case OVERNIGHT -> careScheduleService.createCareSchedule(id);
                    case BUY_SERVICE -> {
                        bookingOrder.getBookingDetails().forEach(detail -> bookingSlotService.updateStatusById(detail.getService().getId(), BookingSlotStatus.BOOKED));
                        careScheduleService.createCareScheduleForBuyService(id);
                    }
                    default -> {
                    }
                }
            }
            case COMPLETED -> {
                bookingOrder = repository.getReferenceById(id);
                if (bookingOrder.getStatus()==BookingOrderStatus.COMPLETED) {
                    return;
                }

                BigDecimal total = calculateTotalBookingPrice(bookingOrder);

                //get commission rate from app save config
                AppSaveConfig config = appSaveConfigService.findByConfigKey(ConfigKey.APP_COMMISSION_SETTING);
                BigDecimal commissionRate = BigDecimal.valueOf(0.05);

                if (config != null && config.getConfigValue() != null) {
                    commissionRate = new BigDecimal(config.getConfigValue());
                }

                if (bookingOrder.getPaymentMethod() == PaymentMethod.MOMO) {
                    transactionService.completeService(id, total);
                }

                transactionService.createCommissionTransaction(bookingOrder.getSitter().getId(), id, total.multiply(commissionRate));
            }
            case NOT_CONFIRMED -> {
                bookingOrder = repository.getReferenceById(id);
                applicationEventPublisher.publishEvent(new NotificationEvent(this, bookingOrder.getUser().getId(),
                        "Đơn đặt lịch của bạn đã bị từ chối.",
                        "Đơn đặt lịch của bạn đã bị từ chối bởi " + bookingOrder.getSitter().getFullName()));

                transactionService.refund(id);
            }
            case CANCELLED -> transactionService.refund(id);
            default -> {
            }
        }
    }

}
