package com.mservice.processor;

import com.mservice.config.Environment;
import com.mservice.enums.Language;
import com.mservice.models.CbTokenInquiryRequest;
import com.mservice.models.CbTokenInquiryResponse;
import com.mservice.models.HttpResponse;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.exception.MoMoException;
import com.mservice.shared.utils.Encoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InquiryCbToken extends AbstractProcess<CbTokenInquiryRequest, CbTokenInquiryResponse> {

    private static final Logger log = LogManager.getLogger(InquiryCbToken.class);

    public InquiryCbToken(Environment environment) {
        super(environment);
    }

    public static CbTokenInquiryResponse process(Environment env, String orderId, String requestId, String partnerClientId) throws Exception {
        try {
            InquiryCbToken m2Processor = new InquiryCbToken(env);

            CbTokenInquiryRequest request = m2Processor.createInquiryTokenRequest(orderId, requestId, partnerClientId);
            CbTokenInquiryResponse cbTokenInquiryResponse = m2Processor.execute(request);

            return cbTokenInquiryResponse;
        } catch (Exception exception) {
            log.error("[TokenInquiryProcess] " + exception);
        }
        return null;
    }

    @Override
    public CbTokenInquiryResponse execute(CbTokenInquiryRequest request) throws MoMoException {
        try {

            String payload = getGson().toJson(request, CbTokenInquiryRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint().getCbTokenInquiryUrl(), payload);

            if (response.getStatus() != 200) {
                throw new MoMoException("[CbTokenInquiryResponse] [" + request.getOrderId() + "] -> Error API");
            }

            System.out.println("uweryei7rye8wyreow8: " + response.getData());

            CbTokenInquiryResponse cbTokenInquiryResponse = getGson().fromJson(response.getData(), CbTokenInquiryResponse.class);
            String responserawData = Parameter.REQUEST_ID + "=" + cbTokenInquiryResponse.getRequestId() +
                    "&" + Parameter.ORDER_ID + "=" + cbTokenInquiryResponse.getOrderId() +
                    "&" + Parameter.MESSAGE + "=" + cbTokenInquiryResponse.getMessage() +
                    "&" + Parameter.RESULT_CODE + "=" + cbTokenInquiryResponse.getResultCode();

            log.info("[CbTokenInquiryResponse] rawData: " + responserawData);

            return cbTokenInquiryResponse;

        } catch (Exception exception) {
            log.error("[CbTokenInquiryResponse] " + exception);
            throw new IllegalArgumentException("Invalid params capture MoMo Request");
        }
    }

    public CbTokenInquiryRequest createInquiryTokenRequest(String orderId, String requestId, String partnerClientId) {
        try {
            String requestRawData = new StringBuilder()
                    .append(Parameter.ACCESS_KEY).append("=").append(partnerInfo.getAccessKey()).append("&")
                    .append(Parameter.ORDER_ID).append("=").append(orderId).append("&")
                    .append(Parameter.PARTNER_CLIENT_ID).append("=").append(partnerClientId).append("&")
                    .append(Parameter.PARTNER_CODE).append("=").append(partnerInfo.getPartnerCode()).append("&")
                    .append(Parameter.REQUEST_ID).append("=").append(requestId)
                    .toString();

            String signRequest = Encoder.signHmacSHA256(requestRawData, partnerInfo.getSecretKey());
            log.debug("[TokenInquiryRequest] rawData: " + requestRawData + ", [Signature] -> " + signRequest);

            return new CbTokenInquiryRequest(partnerInfo.getPartnerCode(), orderId, requestId, Language.EN, partnerClientId, signRequest);
        } catch (Exception e) {
            log.error("[TokenInquiryResponse] " + e);
        }

        return null;
    }
}
