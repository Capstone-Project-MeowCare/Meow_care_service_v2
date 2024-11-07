package com.mservice.processor;

import com.mservice.config.Environment;
import com.mservice.enums.Language;
import com.mservice.models.HttpResponse;
import com.mservice.models.QueryStatusTransactionRequest;
import com.mservice.models.QueryStatusTransactionResponse;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.exception.MoMoException;
import com.mservice.shared.utils.Encoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class QueryTransactionStatus extends AbstractProcess<QueryStatusTransactionRequest, QueryStatusTransactionResponse> {

    private static final Logger log = LogManager.getLogger(QueryTransactionStatus.class);

    public QueryTransactionStatus(Environment environment) {
        super(environment);
    }

    public static QueryStatusTransactionResponse process(Environment env, String orderId, String requestId) throws Exception {
        try {
            QueryTransactionStatus m2Processor = new QueryTransactionStatus(env);

            QueryStatusTransactionRequest request = m2Processor.createQueryTransactionRequest(orderId, requestId);
            QueryStatusTransactionResponse queryTransResponse = m2Processor.execute(request);

            return queryTransResponse;
        } catch (Exception exception) {
            log.error("[QueryTransactionProcess] " + exception);
        }
        return null;
    }

    @Override
    public QueryStatusTransactionResponse execute(QueryStatusTransactionRequest request) throws MoMoException {
        try {

            String payload = getGson().toJson(request, QueryStatusTransactionRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint().getQueryUrl(), payload);

            if (response.getStatus() != 200) {
                throw new MoMoException("[QueryTransactionResponse] [" + request.getOrderId() + "] -> Error API");
            }

            System.out.println("uweryei7rye8wyreow8: " + response.getData());

            QueryStatusTransactionResponse queryStatusTransactionResponse = getGson().fromJson(response.getData(), QueryStatusTransactionResponse.class);
            String responserawData = Parameter.REQUEST_ID + "=" + queryStatusTransactionResponse.getRequestId() +
                    "&" + Parameter.ORDER_ID + "=" + queryStatusTransactionResponse.getOrderId() +
                    "&" + Parameter.MESSAGE + "=" + queryStatusTransactionResponse.getMessage() +
                    "&" + Parameter.RESULT_CODE + "=" + queryStatusTransactionResponse.getResultCode();

            log.info("[QueryTransactionResponse] rawData: " + responserawData);

            return queryStatusTransactionResponse;

        } catch (Exception exception) {
            log.error("[QueryTransactionResponse] " + exception);
            throw new IllegalArgumentException("Invalid params capture MoMo Request");
        }
    }

    public QueryStatusTransactionRequest createQueryTransactionRequest(String orderId, String requestId) {

        try {
            String requestRawData = new StringBuilder()
                    .append(Parameter.ACCESS_KEY).append("=").append(partnerInfo.getAccessKey()).append("&")
                    .append(Parameter.ORDER_ID).append("=").append(orderId).append("&")
                    .append(Parameter.PARTNER_CODE).append("=").append(partnerInfo.getPartnerCode()).append("&")
                    .append(Parameter.REQUEST_ID).append("=").append(requestId)
                    .toString();

            String signRequest = Encoder.signHmacSHA256(requestRawData, partnerInfo.getSecretKey());
            log.debug("[QueryTransactionRequest] rawData: " + requestRawData + ", [Signature] -> " + signRequest);

            return new QueryStatusTransactionRequest(partnerInfo.getPartnerCode(), orderId, requestId, Language.EN, signRequest);
        } catch (Exception e) {
            log.error("[QueryTransactionRequest] " + e);
        }

        return null;
    }
}
