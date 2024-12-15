package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.TransactionDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.Transaction;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.enums.PaymentMethod;
import com.meow_care.meow_care_service.enums.TransactionStatus;
import com.meow_care.meow_care_service.enums.TransactionType;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.TransactionMapper;
import com.meow_care.meow_care_service.repositories.TransactionRepository;
import com.meow_care.meow_care_service.services.TransactionService;
import com.meow_care.meow_care_service.services.WalletHistoryService;
import com.meow_care.meow_care_service.services.WalletService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import com.mservice.config.Environment;
import com.mservice.models.RefundMoMoResponse;
import com.mservice.processor.RefundTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionServiceImpl
        extends BaseServiceImpl<TransactionDto, Transaction, TransactionRepository, TransactionMapper>
        implements TransactionService {

    private final UUID ADMIN_ID = UUID.fromString("e7b8f9a6-5678-4c56-89a7-23456789abcd"); // or another fixed ID

    private final WalletService walletService;

    private final WalletHistoryService walletHistoryService;

    Environment environment = Environment.selectEnv("dev");

    public TransactionServiceImpl(TransactionRepository repository, TransactionMapper mapper,
            WalletService walletService, WalletHistoryService walletHistoryService) {
        super(repository, mapper);
        this.walletService = walletService;
        this.walletHistoryService = walletHistoryService;
    }

    @Override
    public Transaction create(Transaction transaction) {
        return repository.save(transaction);
    }

    @Override
    public void updateTransactionToHolding(UUID id, Long transId) {
        Transaction transaction = repository.findById(id).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Transaction not found"));
        transaction.setStatus(TransactionStatus.HOLDING);
        transaction.setTransId(transId);
        repository.save(transaction);
        walletService.addHoldBalance(transaction.getToUser().getId(), transaction.getAmount());
    }

    @Override
    public void updateStatus(UUID id, TransactionStatus status) {

        int result = updateStatusById(id, status);
        if (result == 0) {

            if (!repository.existsById(id)) {
                throw new ApiException(ApiStatus.ERROR, "Transaction not found");
            }

            throw new ApiException(ApiStatus.ERROR, "Transaction status not updated");
        }
    }

    @Override
    public void transfer(UUID fromUserId, UUID toUserId, BigDecimal amount) {
        walletService.transfer(fromUserId, toUserId, amount);
    }

    //create transaction and transfer money to wallet
    @Override
    public void createPaymentTransactionAndTransFer(UUID fromUserId, UUID toUserId, UUID bookingId, TransactionStatus status, TransactionType transactionType, PaymentMethod paymentMethod, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setBookingId(bookingId);
        transaction.setFromUserId(fromUserId);
        transaction.setToUserId(toUserId);
        transaction.setStatus(status);
        transaction.setTransactionType(transactionType);
        transaction.setPaymentMethod(paymentMethod);
        transfer(fromUserId, toUserId, amount);
        create(transaction);
    }

    // create commission transaction
    @Override
    public void createCommissionTransaction(UUID userId, UUID bookingId, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setBookingId(bookingId);
        transaction.setFromUserId(userId);
        transaction.setToUserId(ADMIN_ID);
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setTransactionType(TransactionType.COMMISSION);
        transaction.setPaymentMethod(PaymentMethod.WALLET);
        transaction = create(transaction);
        updateStatusById(transaction.getId(), TransactionStatus.COMPLETED);
    }

    @Override
    public void completeService(UUID bookingId, BigDecimal amount) {
        List<Transaction> transactions = repository.findByBookingId(bookingId);

        if (transactions.isEmpty()) {
            throw new ApiException(ApiStatus.ERROR, "Transaction not found");
        }

        for (Transaction transaction : transactions) {
            if (transaction.getStatus() == TransactionStatus.HOLDING) {
                updateStatusById(transaction.getId(), TransactionStatus.COMPLETED);
            }
        }

    }

    @Override
    public ApiResponse<List<TransactionDto>> getByUserId(UUID userId) {
        List<Transaction> transactions = repository.findByFromUser_Id(userId);
        transactions.addAll(repository.findByToUser_Id(userId));

        if (transactions.isEmpty()) {
            throw new ApiException(ApiStatus.ERROR, "Transaction not found");
        }

        return ApiResponse.success(mapper.toDtoList(transactions));
    }

    @Override
    public ApiResponse<Page<TransactionDto>> search(UUID userId, TransactionStatus status, PaymentMethod paymentMethod,
            TransactionType transactionType, Instant fromTime, Instant toTime, Pageable pageable) {

        Page<Transaction> transactions = searchEntity(userId, status, paymentMethod, transactionType, fromTime, toTime,
                pageable);

        Page<TransactionDto> transactionDtos = transactions.map(mapper::toDto);

        return ApiResponse.success(transactionDtos);
    }

    @Override
    public ApiResponse<BigDecimal> calculateTotalAmount(UUID userId, TransactionStatus status,
            PaymentMethod paymentMethod, TransactionType transactionType, Instant fromTime, Instant toTime) {
        Page<Transaction> transactions = searchEntity(userId, status, paymentMethod, transactionType, fromTime, toTime,
                Pageable.unpaged());

        BigDecimal totalAmount = transactions.stream().map(Transaction::getAmount).reduce(BigDecimal.ZERO,
                BigDecimal::add);
        return ApiResponse.success(totalAmount);
    }

    private Page<Transaction> searchEntity(UUID userId, TransactionStatus status, PaymentMethod paymentMethod,
            TransactionType transactionType, Instant fromTime, Instant toTime, Pageable pageable) {
        if ((fromTime == null) != (toTime == null) || (fromTime != null && fromTime.isAfter(toTime))) {
            throw new ApiException(ApiStatus.ERROR, "Invalid time range");
        }

        return (fromTime == null)
                ? repository.search(userId, status, paymentMethod, transactionType, pageable)
                : repository.search(userId, status, paymentMethod, transactionType, fromTime, toTime, pageable);
    }

    // refund transaction by bookingId
    @Override
    public void refund(UUID bookingId) {
        List<Transaction> transactions = repository.findByBookingId(bookingId);

        if (transactions.isEmpty()) {
            throw new ApiException(ApiStatus.ERROR, "Transaction not found");
        }

        for (Transaction transaction : transactions) {
            if (transaction.getStatus() == TransactionStatus.HOLDING) {
                UUID refundId = UUID.randomUUID();
                RefundMoMoResponse refundMoMoResponse;
                try {
                    refundMoMoResponse = RefundTransaction.process(environment, refundId.toString(),
                            UUID.randomUUID().toString(), transaction.getAmount().toBigInteger().toString(),
                            transaction.getTransId(), "");
                } catch (Exception e) {
                    throw new ApiException(ApiStatus.ERROR, "Refund failed");
                }

                if (refundMoMoResponse == null) {
                    throw new ApiException(ApiStatus.ERROR, "Refund failed");
                }

                if (refundMoMoResponse.getResultCode() == 0) {
                    updateStatusById(transaction.getId(), TransactionStatus.REFUND_COMPLETED);
                } else {
                    updateStatusById(transaction.getId(), TransactionStatus.REFUND_FAILED);
                    throw new ApiException(ApiStatus.ERROR, "Refund failed");
                }

            }
        }
    }

    private int updateStatusById(UUID id, TransactionStatus status) {

        switch (status) {
            case HOLDING -> throw new ApiException(ApiStatus.FORBIDDEN, "Forbidden update status");
            case COMPLETED -> {
                Transaction transaction = repository.findById(id).orElseThrow(
                        () -> new ApiException(ApiStatus.NOT_FOUND, "Transaction not found"));

                switch (transaction.getTransactionType()) {
                    case PAYMENT -> {
                        // transfer money to user
                        if (transaction.getPaymentMethod() == PaymentMethod.WALLET) {
                            transfer(transaction.getFromUser().getId(), transaction.getToUser().getId(),
                                    transaction.getAmount());
                        } else {
                            walletService.holdBalanceToBalance(transaction.getToUser().getId(),
                                    transaction.getAmount());
                        }
                    }
                    case COMMISSION -> transfer(transaction.getFromUser().getId(), transaction.getToUser().getId(),
                            transaction.getAmount());
                    default -> {}
                }

                // create wallet history
                walletHistoryService.create(transaction);
            }
            default -> {
                // handle other statuses if needed
            }
        }

        return repository.updateStatusById(status, id);
    }
}
