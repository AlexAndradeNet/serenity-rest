/* BlankFactor (C)2023 */
package com.blankfactor.marqeta.digitalbank.qa.api.account;

import com.marqeta.api.commons.CommonAssertions;
import com.marqeta.api.commons.CommonRequests;
import com.marqeta.api.commons.Credentials;
import com.marqeta.api.commons.EnvironmentProperties;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;

public class AccountRequests {

    private static final String SERVICE_PATH = "blankfactor.account.endpoint";
    private static final String SERVICE_PAYLOAD = "blankfactor.account.payload";
    private static final String RESPONSE_SCHEMA = "blankfactor.account.schema";
    @Steps private CommonRequests commonRequests;
    @Steps private CommonAssertions commonAssertions;

    @Step("Create an account in Blankfactor's Bank")
    public void create(Credentials credentials, String cardToken) {
        String customPayload = getCustomPayload(cardToken);

        commonRequests.post(credentials, SERVICE_PATH, customPayload, false);
        commonAssertions.verifyFullCreatedResponseAndSchema(RESPONSE_SCHEMA);
    }

    private static String getCustomPayload(String cardToken) {
        String payload = EnvironmentProperties.getProperty(SERVICE_PAYLOAD);
        payload = payload.replace("CARD_TOKEN", cardToken);
        return payload;
    }
}
