package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.MomoPaymentReturnDto;
import com.meow_care.meow_care_service.dto.booking_order.BookingOrderDto;
import com.meow_care.meow_care_service.dto.booking_order.BookingOrderRequest;
import com.meow_care.meow_care_service.dto.booking_order.BookingOrderWithDetailDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.AppSaveConfig;
import com.meow_care.meow_care_service.entities.BookingDetail;
import com.meow_care.meow_care_service.entities.BookingOrder;
import com.meow_care.meow_care_service.entities.PetProfile;
import com.meow_care.meow_care_service.entities.SitterProfile;
import com.meow_care.meow_care_service.entities.SitterUnavailableDate;
import com.meow_care.meow_care_service.entities.Transaction;
import com.meow_care.meow_care_service.entities.User;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.enums.BookingOrderStatus;
import com.meow_care.meow_care_service.enums.BookingSlotStatus;
import com.meow_care.meow_care_service.enums.ConfigKey;
import com.meow_care.meow_care_service.enums.OrderType;
import com.meow_care.meow_care_service.enums.PaymentMethod;
import com.meow_care.meow_care_service.enums.PetProfileStatus;
import com.meow_care.meow_care_service.enums.ServiceStatus;
import com.meow_care.meow_care_service.enums.ServiceType;
import com.meow_care.meow_care_service.enums.SitterProfileStatus;
import com.meow_care.meow_care_service.enums.TransactionStatus;
import com.meow_care.meow_care_service.enums.TransactionType;
import com.meow_care.meow_care_service.enums.UnavailableDateType;
import com.meow_care.meow_care_service.event.NotificationEvent;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.BookingOrderMapper;
import com.meow_care.meow_care_service.repositories.BookingOrderRepository;
import com.meow_care.meow_care_service.services.AppSaveConfigService;
import com.meow_care.meow_care_service.services.BookingOrderService;
import com.meow_care.meow_care_service.services.BookingSlotService;
import com.meow_care.meow_care_service.services.CareScheduleService;
import com.meow_care.meow_care_service.services.PetProfileService;
import com.meow_care.meow_care_service.services.SitterProfileService;
import com.meow_care.meow_care_service.services.SitterUnavailableDateService;
import com.meow_care.meow_care_service.services.TransactionService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import com.meow_care.meow_care_service.util.UserUtils;
import com.mservice.config.Environment;
import com.mservice.enums.RequestType;
import com.mservice.models.PaymentResponse;
import com.mservice.processor.CreateOrderMoMo;
import com.mservice.shared.utils.Encoder;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class BookingOrderServiceImpl extends BaseServiceImpl<BookingOrderDto, BookingOrder, BookingOrderRepository, BookingOrderMapper> implements BookingOrderService {

    @Value("${momo.callback.order.url}")
    private String momoCallbackUrl;

    private static final Logger log = LoggerFactory.getLogger(BookingOrderServiceImpl.class);

    private final ScheduledExecutorService scheduledExecutorService;

    private final CareScheduleService careScheduleService;

    private final TransactionService transactionService;

    private final AppSaveConfigService appSaveConfigService;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final BookingSlotService bookingSlotService;

    private final SitterProfileService sitterProfileService;

    private final PetProfileService petProfileService;

    private final SitterUnavailableDateService sitterUnavailableDateService;

    Environment environment = Environment.selectEnv("dev");

    public BookingOrderServiceImpl(BookingOrderRepository repository, BookingOrderMapper mapper, ScheduledExecutorService scheduledExecutorService, CareScheduleService careScheduleService, TransactionService transactionService, AppSaveConfigService appSaveConfigService, ApplicationEventPublisher applicationEventPublisher, BookingSlotService bookingSlotService, SitterProfileService sitterProfileService, PetProfileService petProfileService, SitterUnavailableDateService sitterUnavailableDateService) {
        super(repository, mapper);
        this.scheduledExecutorService = scheduledExecutorService;
        this.careScheduleService = careScheduleService;
        this.transactionService = transactionService;
        this.appSaveConfigService = appSaveConfigService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.bookingSlotService = bookingSlotService;
        this.sitterProfileService = sitterProfileService;
        this.petProfileService = petProfileService;
        this.sitterUnavailableDateService = sitterUnavailableDateService;
    }

    @PostConstruct
    public void init() {
        scheduledExecutorService.scheduleAtFixedRate(this::updateOrdersToInProcess, 0, 1, TimeUnit.MINUTES);
    }

    @Transactional
    public void updateOrdersToInProcess() {
        Instant now = Instant.now();

        // Find all orders with status CONFIRMED and start date/time in the past
        List<BookingOrder> orders = repository.findByStatusAndStartDateBefore(BookingOrderStatus.CONFIRMED, now);

        // Update the status of each order to IN PROCESS
        for (BookingOrder order : orders) {
            order.setStatus(BookingOrderStatus.IN_PROGRESS);
            // Use handleStatusUpdate for proper status handling
            handleStatusUpdate(order.getId(), BookingOrderStatus.IN_PROGRESS);
        }

        // Save all updated orders to the database
        repository.saveAll(orders);
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
    public ApiResponse<Boolean> isFullSlot(UUID sitterId, Instant startDate, Instant endDate, Integer numberOfPets) {
        List<BookingOrder> bookingOrders = repository.findBySitter_IdAndStartDateAndEndDateAndStatusIn(sitterId, startDate, endDate, Set.of(
                BookingOrderStatus.CONFIRMED,
                BookingOrderStatus.IN_PROGRESS));

        List<BookingDetail> bookingDetails = bookingOrders.stream().map(BookingOrder::getBookingDetails).flatMap(Collection::stream).toList();
        Set<PetProfile> pets = bookingDetails.stream().map(BookingDetail::getPet).collect(Collectors.toSet());

        SitterProfile sitterProfile = sitterProfileService.getEntityBySitterId(sitterId);

        int totalBookedPets = pets.size();
        int maxPets = sitterProfile.getMaximumQuantity();

        return ApiResponse.success(totalBookedPets + numberOfPets > maxPets);
    }

    @Override
    public ApiResponse<BookingOrderWithDetailDto> createWithDetail(BookingOrderRequest dto) {

        if (dto.bookingDetails().isEmpty()) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Booking details is empty");
        }

        SitterProfile sitterProfile = sitterProfileService.getEntityBySitterId(dto.sitterId());

        if (sitterProfile.getStatus() == SitterProfileStatus.INACTIVE) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Sitter is inactive");
        }

        BookingOrder bookingOrder = mapper.toEntityWithDetail(dto);

        //validate booking detail
        Set<PetProfile> petProfiles = bookingOrder.getBookingDetails().stream().map(BookingDetail::getPet).collect(Collectors.toSet());


        if (petProfiles.size() > sitterProfile.getMaximumQuantity()) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Number of pets is greater than maximum quantity");
        }


        List<BookingOrder> oldOrders;
        if (bookingOrder.getOrderType() == OrderType.OVERNIGHT) {
            oldOrders = repository.findBySitter_IdAndStartDateAndEndDateAndStatusIn(dto.sitterId(), bookingOrder.getStartDate(), bookingOrder.getEndDate(), Set.of(
                    BookingOrderStatus.CONFIRMED,
                    BookingOrderStatus.IN_PROGRESS));
        } else {
            oldOrders = repository.findBySitter_IdAndStartDateAndStatusIn(dto.sitterId(), bookingOrder.getStartDate(), Set.of(
                    BookingOrderStatus.CONFIRMED,
                    BookingOrderStatus.IN_PROGRESS));
        }

        if (!oldOrders.isEmpty()) {
            List<BookingDetail> oldBookingDetails = oldOrders.stream().map(BookingOrder::getBookingDetails).flatMap(Collection::stream).toList();
            Set<PetProfile> oldPets = oldBookingDetails.stream().map(BookingDetail::getPet).collect(Collectors.toSet());

            //old pets not contain new pets
            oldPets.forEach(pet -> {
                if (petProfiles.contains(pet)) {
                    throw new ApiException(ApiStatus.INVALID_REQUEST, "Pet is already booked");
                }
            });

            //check max quantity
            if (oldPets.size() + petProfiles.size() > sitterProfile.getMaximumQuantity()) {
                throw new ApiException(ApiStatus.INVALID_REQUEST, "Sitter is busy");
            }
        }

        //if booking detail type is addition service must have slot id
        bookingOrder.getBookingDetails().forEach(detail -> {
            if (detail.getService().getServiceType() == ServiceType.ADDITION_SERVICE && detail.getBookingSlotId() == null) {
                throw new ApiException(ApiStatus.INVALID_REQUEST, "Slot id is required for addition service");
            }
        });


        bookingOrder.setPaymentStatus(0);
        bookingOrder.setStatus(BookingOrderStatus.AWAITING_PAYMENT);
        bookingOrder.setUser(User.builder().id(UserUtils.getCurrentUserId()).build());

        switch (dto.paymentMethod()) {
            case PAY_LATER -> bookingOrder.setStatus(BookingOrderStatus.CONFIRMED);
            case WALLET -> {
                bookingOrder.setStatus(BookingOrderStatus.CONFIRMED);
                transactionService.createPaymentTransactionAndTransFer(bookingOrder.getUser().getId(), bookingOrder.getSitter().getId(), bookingOrder.getSitter().getId(), TransactionStatus.COMPLETED, TransactionType.PAYMENT, PaymentMethod.WALLET, calculateTotalBookingPrice(bookingOrder));
                throw new ApiException(ApiStatus.NOT_IMPLEMENTED, "Wallet payment method is not implemented yet");
            }
            default -> {
            }
        }

        bookingOrder = repository.saveAndFlush(bookingOrder);

        if (bookingOrder.getOrderType() == OrderType.OVERNIGHT) {
            // Check and create unavailable dates
            LocalDate startDate = bookingOrder.getStartDate()
                    .atZone(ZoneOffset.UTC)
                    .toLocalDate();
            LocalDate endDate = bookingOrder.getEndDate()
                    .atZone(ZoneOffset.UTC)
                    .toLocalDate();

            List<LocalDate> fullDays = getFullPetSlotDaysInternal(
                    bookingOrder.getSitter().getId(),
                    startDate,
                    endDate
            );

            // Create SitterUnavailableDate entries
            for (LocalDate fullDay : fullDays) {
                SitterUnavailableDate unavailableDate = SitterUnavailableDate.builder()
                        .sitterProfile(sitterProfile)
                        .type(UnavailableDateType.DATE)
                        .date(fullDay)
                        .build();
                sitterUnavailableDateService.createInternal(unavailableDate);
            }
        }

        handleStatusUpdate(bookingOrder.getId(), bookingOrder.getStatus());

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

    protected int updateStatusInternal(UUID id, BookingOrderStatus status) {

        handleStatusUpdate(id, status);

        return repository.updateStatusById(status, id);
    }

    @Override
    public ApiResponse<Void> updateStatus(UUID id, BookingOrderStatus status) {
        BookingOrder bookingOrder = repository.findById(id)
                .orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));

        if (status == BookingOrderStatus.CANCELLED) {
            Set<BookingOrderStatus> statuses = Set.of(BookingOrderStatus.IN_PROGRESS);
            if (statuses.contains(bookingOrder.getStatus())) {
                throw new ApiException(ApiStatus.INVALID_REQUEST, "No permission to cancel this booking order");
            }
        }

        handleStatusUpdate(id, status);

        bookingOrder.setStatus(status);
        repository.save(bookingOrder);

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
    public ApiResponse<Long> countByStatusAndUpdatedAtBetween(BookingOrderStatus status, Instant from, Instant to) {
        long count = repository.countByStatusAndUpdatedAtBetween(status, from, to);
        return ApiResponse.success(count);
    }

    //count by sitter id and status, order type
    @Override
    public ApiResponse<Long> countBySitterIdAndStatusAndOrderType(UUID id, @Nullable BookingOrderStatus status, @Nullable OrderType orderType) {
        long count = repository.countBySitter_IdAndStatusAndOrderType(id, status, orderType);
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

                if (updateStatusInternal(bookingOrder.getId(), BookingOrderStatus.CONFIRMED) == 0) {
                    throw new ApiException(ApiStatus.UPDATE_ERROR, "Error while updating booking order status");
                }
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

    public List<LocalDate> getFullPetSlotDaysInternal(UUID sitterId, LocalDate startDate, LocalDate endDate) {
        // Get sitter profile to check max capacity
        SitterProfile sitterProfile = sitterProfileService.getEntityByUserId(sitterId)
                .orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND, "Sitter profile not found"));

        // Get all bookings in date range
        List<BookingOrder> bookings = repository.findBySitterIdAndDateRange(
                sitterId,
                startDate.atStartOfDay().toInstant(ZoneOffset.UTC),
                endDate.atStartOfDay().plusDays(1).toInstant(ZoneOffset.UTC),
                Set.of(BookingOrderStatus.CONFIRMED, BookingOrderStatus.IN_PROGRESS)
        );

        // Group bookings by date and sum pet quantities
        Map<LocalDate, Integer> petsPerDay = new HashMap<>();

        for (BookingOrder booking : bookings) {
            // Skip cancelled/not confirmed bookings
            if (booking.getStatus() == BookingOrderStatus.CANCELLED
                || booking.getStatus() == BookingOrderStatus.NOT_CONFIRMED) {
                continue;
            }

            Set<BookingDetail> oldBookingDetails = booking.getBookingDetails();
            Set<PetProfile> oldPets = oldBookingDetails.stream().map(BookingDetail::getPet).collect(Collectors.toSet());

            // Get booking dates
            LocalDate bookingStart = booking.getStartDate()
                    .atZone(ZoneOffset.UTC)
                    .toLocalDate();
            LocalDate bookingEnd = booking.getEndDate()
                    .atZone(ZoneOffset.UTC)
                    .toLocalDate();

            // Add pet count for each day of booking
            for (LocalDate date = bookingStart; !date.isAfter(bookingEnd); date = date.plusDays(1)) {
                int currentPets = petsPerDay.getOrDefault(date, 0);
                petsPerDay.put(date,
                        currentPets + oldPets.size());
            }
        }

        // Find dates where pet count >= max capacity
        List<LocalDate> fullDays = petsPerDay.entrySet().stream()
                .filter(entry -> entry.getValue() >= sitterProfile.getMaximumQuantity())
                .map(Map.Entry::getKey)
                .sorted()
                .collect(Collectors.toList());

        return fullDays;
    }

    @Override
    public ApiResponse<List<LocalDate>> getFullPetSlotDays(UUID sitterId, LocalDate startDate, LocalDate endDate) {
        List<LocalDate> fullDays = getFullPetSlotDaysInternal(sitterId, startDate, endDate);
        return ApiResponse.success(fullDays);
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

    protected void handleStatusUpdate(UUID id, BookingOrderStatus status) {
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
                Set<PetProfile> petProfiles = bookingOrder.getBookingDetails().stream().map(BookingDetail::getPet).collect(Collectors.toSet());


                applicationEventPublisher.publishEvent(new NotificationEvent(this, bookingOrder.getSitter().getId(),
                        "Bạn có một đơn đặt lịch mới.",
                        "Một đơn đặt lịch mới từ " + bookingOrder.getUser().getFullName()));

                switch (bookingOrder.getOrderType()) {
                    case OVERNIGHT -> {
                        careScheduleService.createCareSchedule(id);


                    }
                    case BUY_SERVICE -> {
                        bookingOrder.getBookingDetails().forEach(detail -> bookingSlotService.updateStatusById(detail.getBookingSlotId(), BookingSlotStatus.BOOKED));
                        careScheduleService.createCareScheduleForBuyService(id);
                    }
                    default -> {
                    }
                }

                updatePetProfileStatus(bookingOrder, PetProfileStatus.IN_ORDER);
            }
            case COMPLETED -> {
                bookingOrder = repository.getReferenceById(id);

                BigDecimal total = calculateTotalBookingPrice(bookingOrder);

                //get commission rate from app save config
                AppSaveConfig config = appSaveConfigService.findByConfigKey(ConfigKey.APP_COMMISSION_SETTING);
                BigDecimal commissionRate = BigDecimal.valueOf(5 / 100.0);

                if (config != null && config.getConfigValue() != null) {
                    commissionRate = BigDecimal.valueOf(Double.parseDouble(config.getConfigValue()) / 100.0);
                }

                if (bookingOrder.getPaymentMethod() == PaymentMethod.MOMO) {
                    transactionService.completeService(id, total);
                }

                updatePetProfileStatus(bookingOrder, PetProfileStatus.ACTIVE);

                transactionService.createCommissionTransaction(bookingOrder.getSitter().getId(), id, total.multiply(commissionRate));
            }
            case NOT_CONFIRMED -> {
                bookingOrder = repository.getReferenceById(id);
                applicationEventPublisher.publishEvent(new NotificationEvent(this, bookingOrder.getUser().getId(),
                        "Đơn đặt lịch của bạn đã bị từ chối.",
                        "Đơn đặt lịch của bạn đã bị từ chối bởi " + bookingOrder.getSitter().getFullName()));

                transactionService.refund(id);
                updatePetProfileStatus(bookingOrder, PetProfileStatus.ACTIVE);
            }
            case CANCELLED -> {
                bookingOrder = repository.getReferenceById(id);
                SitterProfile sitterProfile = sitterProfileService.getEntityBySitterId(bookingOrder.getSitter().getId());
                int fullRefundDay = sitterProfile.getFullRefundDay();

                if (bookingOrder.getStartDate().isBefore(Instant.now().plus(fullRefundDay, ChronoUnit.DAYS))) {
                    transactionService.refund(id);
                }

                //if start date is after refund day and not start yet, refund 50%
                if (bookingOrder.getStartDate().isAfter(Instant.now().plus(fullRefundDay, ChronoUnit.DAYS))) {
                    transactionService.refund(id, 50);
                }

                updatePetProfileStatus(bookingOrder, PetProfileStatus.ACTIVE);
            }
            default -> {
            }
        }
    }

    private void updatePetProfileStatus(BookingOrder bookingOrder, PetProfileStatus status) {
        Set<BookingDetail> bookingDetails = bookingOrder.getBookingDetails();
        Set<PetProfile> petProfiles = bookingDetails.stream().map(BookingDetail::getPet).collect(Collectors.toSet());
        SitterProfile sitterProfile = sitterProfileService.getEntityBySitterId(bookingOrder.getSitter().getId());
        petProfiles.forEach(pet -> {
            if (petProfileService.updateStatusInternal(pet.getId(), status) == 0) {
                throw new ApiException(ApiStatus.UPDATE_ERROR, "Error while updating pet profile status");
            }
        });
    }

}
