package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.MomoPaymentReturnDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.Transaction;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.enums.PaymentMethod;
import com.meow_care.meow_care_service.enums.TransactionStatus;
import com.meow_care.meow_care_service.enums.TransactionType;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.services.TopUpService;
import com.meow_care.meow_care_service.services.TransactionService;
import com.meow_care.meow_care_service.services.WalletService;
import com.mservice.config.Environment;
import com.mservice.enums.RequestType;
import com.mservice.models.PaymentResponse;
import com.mservice.processor.CreateOrderMoMo;
import com.mservice.shared.utils.Encoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopUpServiceImpl implements TopUpService {

    private final TransactionService transactionService;

    private final WalletService walletService;

    private final Environment environment = Environment.selectEnv("dev");

    @Value("${momo.callback.top-up.url}")
    private String callBackUrl;

    @Override
    public ApiResponse<PaymentResponse> topUpByMomo(UUID userId, BigDecimal amount, String redirectUrl, RequestType requestType) {
        UUID transactionId = UUID.randomUUID();

        // Create MoMo payment request
        String orderInfo = "Top up wallet";
        PaymentResponse paymentResponse;
        try {
            paymentResponse = CreateOrderMoMo.process(
                    environment,
                    transactionId.toString(),
                    UUID.randomUUID().toString(),
                    amount.toString(),
                    orderInfo,
                    redirectUrl,
                    callBackUrl,
                    "",
                    requestType, true);
        } catch (Exception e) {
            throw new ApiException(ApiStatus.ERROR, e.getMessage());
        }

        // Create transaction first
        assert paymentResponse != null;
        Transaction transaction = Transaction.builder()
                .id(transactionId)
                .amount(amount)
                .transactionType(TransactionType.TOP_UP)
                .status(TransactionStatus.PENDING)
                .paymentMethod(PaymentMethod.MOMO)
                .build();
        transaction.setToUserId(userId);
        transactionService.create(transaction);

        return ApiResponse.success(paymentResponse);
    }

    @Override
    public ApiResponse<Void> handleMomoTopUpCallback(MomoPaymentReturnDto momoPaymentReturnDto) {

        try {
            String signature = Encoder.signHmacSHA256(momoPaymentReturnDto.toMap(), environment);

            if (!signature.equals(momoPaymentReturnDto.signature())) {
                log.error("Signature not match");
                throw new ApiException(ApiStatus.SIGNATURE_NOT_MATCH, "Signature not match");
            }

            UUID transactionId = UUID.fromString(momoPaymentReturnDto.orderId());

            Transaction transaction = transactionService.findEntityById(transactionId);

            if (transaction == null) {
                throw new ApiException(ApiStatus.NOT_FOUND, "Transaction not found");
            }

            if (momoPaymentReturnDto.resultCode() == 0) {
                // Payment successful
                transactionService.updateStatus(transactionId, TransactionStatus.COMPLETED);

                // Add balance to wallet
                walletService.addBalance(transaction.getToUser().getId(), transaction.getAmount());

            } else {
                transactionService.updateStatus(transactionId, TransactionStatus.FAILED);
            }

            return ApiResponse.success();
        } catch (Exception e) {
            log.error("Error processing MoMo callback", e);
            throw new ApiException(ApiStatus.ERROR, "Error processing MoMo callback");
        }
    }

}
