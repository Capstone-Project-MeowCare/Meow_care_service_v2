package com.mservice.processor;

import com.mservice.config.Environment;
import com.mservice.enums.Language;
import com.mservice.models.BindingTokenRequest;
import com.mservice.models.BindingTokenResponse;
import com.mservice.models.HttpResponse;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.exception.MoMoException;
import com.mservice.shared.utils.Encoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class BindingToken extends AbstractProcess<BindingTokenRequest, BindingTokenResponse> {

    private static final Logger log = LogManager.getLogger(BindingToken.class);

    public BindingToken(Environment environment) {
        super(environment);
    }

    public static BindingTokenResponse process(Environment env, String orderId, String requestId, String partnerClientId, String callbackToken) {
        try {
            BindingToken m2Processor = new BindingToken(env);

            BindingTokenRequest request = m2Processor.createBindingTokenRequest(orderId, requestId, partnerClientId, callbackToken);
            BindingTokenResponse bindingTokenResponse = m2Processor.execute(request);

            return bindingTokenResponse;
        } catch (Exception exception) {
            log.error("[BindingTokenProcess] " + exception);
        }
        return null;
    }

    @Override
    public BindingTokenResponse execute(BindingTokenRequest request) throws MoMoException {
        try {

            String payload = getGson().toJson(request, BindingTokenRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint().getTokenBindUrl(), payload);

            if (response.getStatus() != 200) {
                throw new MoMoException("[BindingTokenResponse] [" + request.getOrderId() + "] -> Error API");
            }


            BindingTokenResponse BindingTokenResponse = getGson().fromJson(response.getData(), BindingTokenResponse.class);

            return BindingTokenResponse;

        } catch (Exception exception) {
            log.error("[BindingTokenResponse] " + exception);
            throw new IllegalArgumentException("Invalid params capture MoMo Request");
        }
    }

    public BindingTokenRequest createBindingTokenRequest(String orderId, String requestId, String partnerClientId, String callbackToken) {

        try {
            String requestRawData = new StringBuilder()
                    .append(Parameter.ACCESS_KEY).append("=").append(partnerInfo.getAccessKey()).append("&")
                    .append(Parameter.CALLBACK_TOKEN).append("=").append(callbackToken).append("&")
                    .append(Parameter.ORDER_ID).append("=").append(orderId).append("&")
                    .append(Parameter.PARTNER_CLIENT_ID).append("=").append(partnerClientId).append("&")
                    .append(Parameter.PARTNER_CODE).append("=").append(partnerInfo.getPartnerCode()).append("&")
                    .append(Parameter.REQUEST_ID).append("=").append(requestId)
                    .toString();

            String signRequest = Encoder.signHmacSHA256(requestRawData, partnerInfo.getSecretKey());

            return new BindingTokenRequest(partnerInfo.getPartnerCode(), orderId, requestId, Language.EN, partnerClientId, callbackToken, signRequest);
        } catch (Exception e) {
            log.error("[BindingTokenResponse] " + e);
        }

        return null;
    }

}
