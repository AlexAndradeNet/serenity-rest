/* BlankFactor (C)2023 */
package com.marqeta.api.simulate.authorization;

import com.marqeta.api.commons.CommonAssertions;
import com.marqeta.api.commons.CommonRequests;
import com.marqeta.api.commons.Credentials;
import com.marqeta.api.commons.EnvironmentProperties;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;

public class AuthorizationRequests {

    private static final String SERVICE_PATH = "marqeta.simulate.authorization.endpoint";
    private static final String SERVICE_PAYLOAD = "marqeta.simulate.authorization.payload";
    private static final String RESPONSE_SCHEMA = "marqeta.simulate.authorization.schema";
    @Steps private CommonRequests commonRequests;
    @Steps private CommonAssertions commonAssertions;

    public String authorize(Credentials credentials, String cardToken) {
        return authorize(credentials, cardToken, 1000);
    }

    @Step("Authorize a transaction")
    public String authorize(Credentials credentials, String cardToken, int amount) {
        String customPayload = getCustomPayload(cardToken, amount);

        commonRequests.post(credentials, SERVICE_PATH, customPayload, false);
        commonAssertions.verifyFullCreatedResponseAndSchema(RESPONSE_SCHEMA);
        return commonAssertions.validateIfTheTokenIsAGuidAndGetIt(AuthorizationResponse.TOKEN);
    }

    private String getCustomPayload(String cardToken, int amount) {
        String payload = EnvironmentProperties.getProperty(AuthorizationRequests.SERVICE_PAYLOAD);
        payload = payload.replace("AMOUNT_VALUE", String.valueOf(amount));
        payload = payload.replace("CARD_TOKEN", cardToken);
        return payload;
    }

    public void verifyTransactionState(String transactionState) {
        commonAssertions.validateFieldValue(AuthorizationResponse.STATE, transactionState);
    }
}
