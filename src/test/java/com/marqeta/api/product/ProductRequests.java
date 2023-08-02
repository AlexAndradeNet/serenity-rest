/* BlankFactor (C)2023 */
package com.marqeta.api.product;

import com.marqeta.api.commons.CommonAssertions;
import com.marqeta.api.commons.CommonRequests;
import com.marqeta.api.commons.EnvironmentProperties;

public class ProductRequests {

    private static final String SERVICE_PATH = "marqeta.product.endpoint";
    private static final String SERVICE_PAYLOAD = "marqeta.product.payload";
    private static final String RESPONSE_SCHEMA = "marqeta.product.schema";
    private static final String PRODUCT_TOKEN = "AutomationCard" + System.currentTimeMillis();

    private ProductRequests() {
        // Prevents instantiation
    }

    public static String create(CommonRequests commonRequests, String fundingToken) {
        String customPayload = getCustomPayload(fundingToken);

        commonRequests.post(SERVICE_PATH, customPayload, false);
        CommonAssertions.verifyFullCreatedResponseAndSchema(RESPONSE_SCHEMA);
        CommonAssertions.validateFieldValue(ProductResponse.TOKEN, PRODUCT_TOKEN);
        return PRODUCT_TOKEN;
    }

    private static String getCustomPayload(String fundingToken) {
        String payload = EnvironmentProperties.getProperty(ProductRequests.SERVICE_PAYLOAD);
        payload = payload.replace("PRODUCT_TOKEN", PRODUCT_TOKEN);
        payload = payload.replace("FUNDING_TOKEN", fundingToken);
        return payload;
    }
}
