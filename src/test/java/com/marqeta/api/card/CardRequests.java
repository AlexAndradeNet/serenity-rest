/* BlankFactor (C)2023 */
package com.marqeta.api.card;

import com.marqeta.api.commons.CommonAssertions;
import com.marqeta.api.commons.CommonRequests;
import com.marqeta.api.commons.EnvironmentProperties;

public class CardRequests {

    private static final String SERVICE_PATH = "marqeta.card.endpoint";
    private static final String SERVICE_PAYLOAD = "marqeta.card.payload";
    private static final String RESPONSE_SCHEMA = "marqeta.card.schema";

    private CardRequests() {
        // Private constructor to prevent instantiation
    }

    public static String create(
            CommonRequests commonRequests, String userToken, String productToken) {
        String customPayload = getCustomPayload(userToken, productToken);

        commonRequests.post(SERVICE_PATH, customPayload, false);
        CommonAssertions.verifyFullCreatedResponseAndSchema(RESPONSE_SCHEMA);
        return CommonAssertions.validateIfTheTokenIsAGuidAndGetIt(CardResponse.TOKEN);
    }

    private static String getCustomPayload(String userToken, String productToken) {
        String payload = EnvironmentProperties.getProperty(CardRequests.SERVICE_PAYLOAD);
        payload = payload.replace("USER_TOKEN", userToken);
        payload = payload.replace("PRODUCT_TOKEN", productToken);
        return payload;
    }
}
