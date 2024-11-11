package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.MomoPaymentReturnDto;
import com.meow_care.meow_care_service.dto.TransactionDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.Transaction;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.enums.BookingOrderStatus;
import com.meow_care.meow_care_service.enums.TransactionStatus;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.TransactionMapper;
import com.meow_care.meow_care_service.repositories.BookingOrderRepository;
import com.meow_care.meow_care_service.repositories.TransactionRepository;
import com.meow_care.meow_care_service.services.TransactionService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import com.mservice.config.Environment;
import com.mservice.shared.utils.Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionServiceImpl extends BaseServiceImpl<TransactionDto, Transaction, TransactionRepository, TransactionMapper>
        implements TransactionService {
    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final BookingOrderRepository bookingOrderRepository;

    public TransactionServiceImpl(TransactionRepository repository, TransactionMapper mapper, BookingOrderRepository bookingOrderRepository) {
        super(repository, mapper);
        this.bookingOrderRepository = bookingOrderRepository;
    }

    @Override
    public Transaction create(Transaction transaction) {
        return repository.save(transaction);
    }

    @Override
    public ApiResponse<Void> momoCallback(MomoPaymentReturnDto momoPaymentReturnDto) {
        Environment environment = Environment.selectEnv("dev");
        try {
            String signature = Encoder.signHmacSHA256(momoPaymentReturnDto.toMap(), environment);

            if (!signature.equals(momoPaymentReturnDto.signature())) {
                throw new ApiException(ApiStatus.SIGNATURE_NOT_MATCH, "Signature not match");
            }

            Transaction transaction = repository.findById(UUID.fromString(momoPaymentReturnDto.orderId())).orElseThrow(
                    () -> new ApiException(ApiStatus.NOT_FOUND,"Transaction not found")
            );

            if (momoPaymentReturnDto.resultCode() == 0 || momoPaymentReturnDto.resultCode() == 9000) {
                transaction.setStatus(TransactionStatus.COMPLETED);
                bookingOrderRepository.updateStatusById(BookingOrderStatus.AWAITING_CONFIRM, transaction.getBooking().getId());
            } else {
                transaction.setStatus(TransactionStatus.FAILED);
            }

            repository.save(transaction);
            return ApiResponse.success(null);
        } catch (Exception e) {
            log.error("Error while verifying signature", e);
            throw new ApiException(ApiStatus.ERROR, "Error while verifying signature");
        }
    }
}
