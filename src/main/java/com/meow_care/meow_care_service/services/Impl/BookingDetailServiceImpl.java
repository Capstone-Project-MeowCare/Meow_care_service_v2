package com.meow_care.meow_care_service.services.Impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meow_care.meow_care_service.dto.BookingDetailDto;
import com.meow_care.meow_care_service.dto.MomoPaymentReturnDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.BookingDetail;
import com.meow_care.meow_care_service.entities.BookingOrder;
import com.meow_care.meow_care_service.entities.Task;
import com.meow_care.meow_care_service.entities.Transaction;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.enums.BookingOrderStatus;
import com.meow_care.meow_care_service.enums.PaymentMethod;
import com.meow_care.meow_care_service.enums.TaskStatus;
import com.meow_care.meow_care_service.enums.TransactionStatus;
import com.meow_care.meow_care_service.enums.TransactionType;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.BookingDetailMapper;
import com.meow_care.meow_care_service.repositories.BookingDetailRepository;
import com.meow_care.meow_care_service.repositories.BookingOrderRepository;
import com.meow_care.meow_care_service.repositories.TaskRepository;
import com.meow_care.meow_care_service.services.BookingDetailService;
import com.meow_care.meow_care_service.services.TransactionService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import com.mservice.config.Environment;
import com.mservice.enums.RequestType;
import com.mservice.models.PaymentResponse;
import com.mservice.processor.CreateOrderMoMo;
import com.mservice.shared.utils.Encoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class BookingDetailServiceImpl extends BaseServiceImpl<BookingDetailDto, BookingDetail, BookingDetailRepository, BookingDetailMapper> implements BookingDetailService {

    private final BookingOrderRepository bookingOrderRepository;

    private final TransactionService transactionService;

    private final TaskRepository taskRepository;

    public BookingDetailServiceImpl(BookingDetailRepository repository, BookingDetailMapper mapper, BookingOrderRepository bookingOrderRepository, TransactionService transactionService, TaskRepository taskRepository) {
        super(repository, mapper);
        this.bookingOrderRepository = bookingOrderRepository;
        this.transactionService = transactionService;
        this.taskRepository = taskRepository;
    }

    @Override
    public ApiResponse<List<BookingDetailDto>> addAdditionBookingDetail(UUID bookingId, List<BookingDetailDto> detailDtos) {

        BookingOrder bookingOrder = bookingOrderRepository.findById(bookingId).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Booking order not found")
        );
        List<BookingDetail> bookingDetails = mapper.toEntity(detailDtos);
        bookingDetails.forEach(bookingDetail -> bookingDetail.setBooking(bookingOrder));

        repository.saveAll(bookingDetails);
        bookingOrderRepository.updateStatusById(BookingOrderStatus.AWAITING_PAYMENT_ADDITION, bookingId);
        return ApiResponse.success(mapper.toDtoList(bookingDetails));
    }

    @Override
    public ApiResponse<PaymentResponse> createPaymentUrlAdditionBookingDetail(UUID bookingId, RequestType requestType, String callBackUrl, String redirectUrl) throws Exception {
        Environment environment = Environment.selectEnv("dev");

        BookingOrder bookingOrder = bookingOrderRepository.findById(bookingId).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Booking order not found")
        );

        //list all booking detail not have transaction
        List<BookingDetail> bookingDetails = repository.findAllByBookingId(bookingId).stream()
                .filter(bookingDetail -> bookingDetail.getTransactions().isEmpty())
                .toList();

        //calculate total amount
        long days = bookingOrder.getStartDate().until(bookingOrder.getEndDate(), ChronoUnit.DAYS) + 1;
        long totalAmount = bookingDetails.stream()
            .mapToLong(bookingDetail -> bookingDetail.getService().getPrice() * bookingDetail.getQuantity() * days)
                .sum();

        UUID transactionId = UUID.randomUUID();

        List<UUID> bookingDetailIds = bookingDetails.stream().map(BookingDetail::getId).toList();

        PaymentResponse paymentResponse = CreateOrderMoMo.process(environment, transactionId.toString(), UUID.randomUUID().toString(),
                Long.toString(totalAmount), "Pay With MoMo", redirectUrl, callBackUrl, encodeBookingDetailIds(bookingDetailIds), requestType, Boolean.TRUE);

        //create transaction
        transactionService.create(Transaction.builder()
                .booking(bookingOrder)
                .bookingDetails(new LinkedHashSet<>(bookingDetails))
                .amount(BigDecimal.valueOf(totalAmount))
                .status(TransactionStatus.PENDING)
                .transactionType(TransactionType.BOOKING)
                .paymentMethod(PaymentMethod.WALLET)
                .fromUser(bookingOrder.getUser())
                .toUser(bookingOrder.getSitter())
                .status(TransactionStatus.PENDING)
                .build());

        return ApiResponse.success(paymentResponse);
    }

    @Override
    public ApiResponse<Void> momoCallback(MomoPaymentReturnDto momoPaymentReturnDto) {
        Environment environment = Environment.selectEnv("dev");
        try {
            String signature = Encoder.signHmacSHA256(momoPaymentReturnDto.toMap(), environment);

            if (!signature.equals(momoPaymentReturnDto.signature())) {
                throw new ApiException(ApiStatus.SIGNATURE_NOT_MATCH, "Signature not match");
            }

            UUID transactionId = UUID.fromString(momoPaymentReturnDto.orderId());
            Transaction transaction = transactionService.findEntityById(transactionId);

            if (momoPaymentReturnDto.resultCode() == 0 || momoPaymentReturnDto.resultCode() == 9000) {
                // update transaction status to transfer money from user wallet to system wallet
                transactionService.updateStatus(transactionId, TransactionStatus.COMPLETED);

                List<UUID> bookingDetailIds = decodeBookingDetailIds(momoPaymentReturnDto.extraData());
                List<Task>tasks = new ArrayList<>();
                for (UUID bookingDetailId : bookingDetailIds) {
                    BookingDetail bookingDetail = repository.findById(bookingDetailId).orElseThrow(
                            () -> new ApiException(ApiStatus.NOT_FOUND, "Booking detail not found")
                    );
                    Task task = Task.builder()
                            .name(bookingDetail.getService().getName())
                            .description(bookingDetail.getService().getActionDescription())
                            .taskParent(Task.builder().id(bookingDetail.getTaskParentId()).build())
                            .status(TaskStatus.PENDING)
                            .build();
                    tasks.add(task);
                }
                if (!tasks.isEmpty()) {
                    taskRepository.saveAll(tasks);
                }

                bookingOrderRepository.updateStatusById(BookingOrderStatus.IN_PROGRESS, transaction.getBooking().getId());
            } else {
                transactionService.updateStatus(transactionId, TransactionStatus.FAILED);
            }
            return ApiResponse.noBodyContent();
        } catch (Exception e) {
            throw new ApiException(ApiStatus.ERROR, "Error while verifying signature");
        }
    }

    //to base64
    private String encodeBookingDetailIds(List<UUID> bookingDetailIds) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, List<UUID>> map = new HashMap<>();

        map.put("bookingDetailIds", bookingDetailIds);

        // Convert the list of UUIDs to a JSON string
        String json = objectMapper.writeValueAsString(map);
        // Encode the JSON string to a base64-encoded string
        return Base64.getEncoder().encodeToString(json.getBytes());
    }

    //from base64
    private List<UUID> decodeBookingDetailIds(String encodedBookingDetailIds) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        // Decode the base64-encoded string to a JSON string
        String json = new String(Base64.getDecoder().decode(encodedBookingDetailIds));
        // Convert the JSON string to a list of UUIDs
        Map<String, List<UUID>> map = objectMapper.readValue(json, new TypeReference<>() {
        });
        return map.get("bookingDetailIds");
    }
}
