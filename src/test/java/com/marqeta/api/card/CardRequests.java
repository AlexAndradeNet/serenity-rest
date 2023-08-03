/* BlankFactor (C)2023 */
package com.marqeta.api.card;

import com.marqeta.api.commons.CommonAssertions;
import com.marqeta.api.commons.CommonRequests;
import com.marqeta.api.commons.Credentials;
import com.marqeta.api.commons.EnvironmentProperties;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;

public class CardRequests {

    private static final String SERVICE_PATH = "marqeta.card.endpoint";
    private static final String SERVICE_PAYLOAD = "marqeta.card.payload";
    private static final String RESPONSE_SCHEMA = "marqeta.card.schema";
    @Steps private CommonRequests commonRequests;
    @Steps private CommonAssertions commonAssertions;

    @Step("Create a card")
    public String create(Credentials credentials, String userToken, String productToken) {
        String customPayload = getCustomPayload(userToken, productToken);

        commonRequests.post(credentials, SERVICE_PATH, customPayload, false);
        commonAssertions.verifyFullCreatedResponseAndSchema(RESPONSE_SCHEMA);
        return commonAssertions.validateIfTheTokenIsAGuidAndGetIt(CardResponse.TOKEN);
    }

    private static String getCustomPayload(String userToken, String productToken) {
        String payload = EnvironmentProperties.getProperty(CardRequests.SERVICE_PAYLOAD);
        payload = payload.replace("USER_TOKEN", userToken);
        payload = payload.replace("PRODUCT_TOKEN", productToken);
        return payload;
    }
}
