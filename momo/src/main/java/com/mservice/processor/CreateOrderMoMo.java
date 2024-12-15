package com.mservice.processor;

import com.mservice.config.Environment;
import com.mservice.enums.Language;
import com.mservice.enums.RequestType;
import com.mservice.models.HttpResponse;
import com.mservice.models.PaymentRequest;
import com.mservice.models.PaymentResponse;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.exception.MoMoException;
import com.mservice.shared.utils.Encoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author hainguyen
 * Documention: https://developers.momo.vn
 */
public class CreateOrderMoMo extends AbstractProcess<PaymentRequest, PaymentResponse> {

    private static final Logger log = LogManager.getLogger(CreateOrderMoMo.class);

    public CreateOrderMoMo(Environment environment) {
        super(environment);
    }

    /**
     * Capture MoMo Process on Payment Gateway
     *
     * @param amount
     * @param extraData
     * @param orderInfo
     * @param env       name of the environment (dev or prod)
     * @param orderId   unique order ID in MoMo system
     * @param requestId request ID
     * @param returnURL URL to redirect customer
     * @param notifyURL URL for MoMo to return transaction status to merchant
     * @return PaymentResponse
     **/

    public static PaymentResponse process(Environment env, String orderId, String requestId, String amount, String orderInfo, String returnURL, String notifyURL, String extraData, RequestType requestType, Boolean autoCapture) throws Exception {
        try {
            CreateOrderMoMo m2Processor = new CreateOrderMoMo(env);

            PaymentRequest request = m2Processor.createPaymentCreationRequest(orderId, requestId, amount, orderInfo, returnURL, notifyURL, extraData, requestType, autoCapture);
            PaymentResponse captureMoMoResponse = m2Processor.execute(request);

            return captureMoMoResponse;
        } catch (Exception exception) {
            throw new MoMoException(exception.getMessage());
        }
    }

    @Override
    public PaymentResponse execute(PaymentRequest request) throws MoMoException {
        try {

            String payload = getGson().toJson(request, PaymentRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint().getCreateUrl(), payload);

            if (response.getStatus() != 200) {
                //log response
                log.info("[CreateOrderMoMo] " + response.getData());
                throw new MoMoException("[PaymentResponse] [" + request.getOrderId() + "] -> Error API");
            }


            PaymentResponse captureMoMoResponse = getGson().fromJson(response.getData(), PaymentResponse.class);

            return captureMoMoResponse;

        } catch (Exception exception) {
            log.error("[PaymentMoMoResponse] " + exception);
            throw new IllegalArgumentException("Invalid params capture MoMo Request");
        }
    }

    /**
     * @param orderId
     * @param requestId
     * @param amount
     * @param orderInfo
     * @param returnUrl
     * @param notifyUrl
     * @param extraData
     * @return
     */
    public PaymentRequest createPaymentCreationRequest(String orderId, String requestId, String amount, String orderInfo,
                                                       String returnUrl, String notifyUrl, String extraData, RequestType requestType, Boolean autoCapture) {

        try {
            String requestRawData = new StringBuilder()
                    .append(Parameter.ACCESS_KEY).append("=").append(partnerInfo.getAccessKey()).append("&")
                    .append(Parameter.AMOUNT).append("=").append(amount).append("&")
                    .append(Parameter.EXTRA_DATA).append("=").append(extraData).append("&")
                    .append(Parameter.IPN_URL).append("=").append(notifyUrl).append("&")
                    .append(Parameter.ORDER_ID).append("=").append(orderId).append("&")
                    .append(Parameter.ORDER_INFO).append("=").append(orderInfo).append("&")
                    .append(Parameter.PARTNER_CODE).append("=").append(partnerInfo.getPartnerCode()).append("&")
                    .append(Parameter.REDIRECT_URL).append("=").append(returnUrl).append("&")
                    .append(Parameter.REQUEST_ID).append("=").append(requestId).append("&")
                    .append(Parameter.REQUEST_TYPE).append("=").append(requestType.getRequestType())
                    .toString();

            String signRequest = Encoder.signHmacSHA256(requestRawData, partnerInfo.getSecretKey());
//            log.info("[PaymentRequest] rawData: " + requestRawData + ", [Signature] -> " + signRequest);

            return new PaymentRequest(partnerInfo.getPartnerCode(), orderId, requestId, Language.EN, orderInfo, Long.valueOf(amount), "test MoMo", null, requestType,
                    returnUrl, notifyUrl, "test store ID", extraData, null, autoCapture, null, signRequest);
        } catch (Exception e) {
            log.error("[PaymentRequest] " + e);
        }

        return null;
    }

}
