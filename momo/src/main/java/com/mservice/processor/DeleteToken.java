package com.mservice.processor;

import com.mservice.config.Environment;
import com.mservice.enums.Language;
import com.mservice.models.DeleteTokenRequest;
import com.mservice.models.DeleteTokenResponse;
import com.mservice.models.HttpResponse;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.exception.MoMoException;
import com.mservice.shared.utils.Encoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteToken extends AbstractProcess<DeleteTokenRequest, DeleteTokenResponse> {

    private static final Logger log = LogManager.getLogger(DeleteToken.class);

    public DeleteToken(Environment environment) {
        super(environment);
    }

    public static DeleteTokenResponse process(Environment env, String requestId, String orderId, String partnerClientId, String token) {
        try {
            DeleteToken m2Processor = new DeleteToken(env);

            DeleteTokenRequest request = m2Processor.createDeleteTokenRequest(orderId, requestId, partnerClientId, token);
            DeleteTokenResponse response = m2Processor.execute(request);

            return response;
        } catch (Exception exception) {
            log.error("[DeleteTokenProcess] " + exception);
        }
        return null;
    }

    @Override
    public DeleteTokenResponse execute(DeleteTokenRequest request) throws MoMoException {
        try {

            String payload = getGson().toJson(request, DeleteTokenRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint().getTokenDeleteUrl(), payload);

            if (response.getStatus() != 200) {
                throw new MoMoException("[DeleteTokenResponse] [" + request.getOrderId() + "] -> Error API");
            }

            System.out.println("uweryei7rye8wyreow8: " + response.getData());

            DeleteTokenResponse deleteTokenResponse = getGson().fromJson(response.getData(), DeleteTokenResponse.class);
            String responserawData = Parameter.REQUEST_ID + "=" +
                    "&" + Parameter.ORDER_ID + "=" + deleteTokenResponse.getOrderId() +
                    "&" + Parameter.MESSAGE + "=" + deleteTokenResponse.getMessage() +
                    "&" + Parameter.RESULT_CODE + "=" + deleteTokenResponse.getResultCode();

            log.info("[DeleteTokenResponse] rawData: " + responserawData);

            return deleteTokenResponse;

        } catch (Exception exception) {
            log.error("[DeleteTokenResponse] " + exception);
            throw new IllegalArgumentException("Invalid params capture MoMo Request");
        }
    }

    public DeleteTokenRequest createDeleteTokenRequest(String orderId, String requestId, String partnerClientId, String token) {
        try {
            String requestRawData = new StringBuilder()
                    .append(Parameter.ACCESS_KEY).append("=").append(partnerInfo.getAccessKey()).append("&")
                    .append(Parameter.ORDER_ID).append("=").append(orderId).append("&")
                    .append(Parameter.PARTNER_CLIENT_ID).append("=").append(partnerClientId).append("&")
                    .append(Parameter.PARTNER_CODE).append("=").append(partnerInfo.getPartnerCode()).append("&")
                    .append(Parameter.REQUEST_ID).append("=").append(requestId).append("&")
                    .append(Parameter.TOKEN).append("=").append(token)
                    .toString();

            String signRequest = Encoder.signHmacSHA256(requestRawData, partnerInfo.getSecretKey());
            log.debug("[DeleteTokenRequest] rawData: " + requestRawData + ", [Signature] -> " + signRequest);

            return new DeleteTokenRequest(partnerInfo.getPartnerCode(), orderId, requestId, Language.EN, partnerClientId, token, signRequest);
        } catch (Exception e) {
            log.error("[DeleteTokenRequest] " + e);
        }

        return null;
    }
}
