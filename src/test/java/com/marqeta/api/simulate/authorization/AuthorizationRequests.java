/* BlankFactor (C)2023 */
package com.marqeta.api.simulate.authorization;

import com.marqeta.api.commons.CommonAssertions;
import com.marqeta.api.commons.CommonRequests;
import com.marqeta.api.commons.EnvironmentProperties;

public class AuthorizationRequests {

    private static final String SERVICE_PATH = "marqeta.simulate.authorization.endpoint";
    private static final String SERVICE_PAYLOAD = "marqeta.simulate.authorization.payload";
    private static final String RESPONSE_SCHEMA = "marqeta.simulate.authorization.schema";

    private AuthorizationRequests() {
        // Private constructor to prevent instantiation
    }

    public static String authorize(CommonRequests commonRequests, String cardToken) {
        return authorize(commonRequests, cardToken, 1000);
    }

    public static String authorize(CommonRequests commonRequests, String cardToken, int amount) {
        String customPayload = getCustomPayload(cardToken, amount);

        commonRequests.post(SERVICE_PATH, customPayload, false);
        CommonAssertions.verifyFullCreatedResponseAndSchema(RESPONSE_SCHEMA);
        // TODO: Validate the response for "PENDING" state
        return CommonAssertions.validateIfTheTokenIsAGuidAndGetIt(AuthorizationResponse.TOKEN);
    }

    private static String getCustomPayload(String cardToken, int amount) {
        String payload = EnvironmentProperties.getProperty(AuthorizationRequests.SERVICE_PAYLOAD);
        payload = payload.replace("AMOUNT_VALUE", String.valueOf(amount));
        payload = payload.replace("CARD_TOKEN", cardToken);
        return payload;
    }

    public static void verifyTransactionState(String transactionState) {
        CommonAssertions.validateFieldValue(AuthorizationResponse.STATE, transactionState);
    }
}
