/* BlankFactor (C)2023 */
package com.marqeta.api.user;

import com.marqeta.api.commons.CommonAssertions;
import com.marqeta.api.commons.CommonRequests;
import com.marqeta.api.commons.EnvironmentProperties;

public class UserRequests {

    private static final String SERVICE_PATH = "marqeta.user.endpoint";
    private static final String SERVICE_PAYLOAD = "marqeta.user.payload";
    private static final String RESPONSE_SCHEMA = "marqeta.user.schema";
    private static final String USER_TOKEN = "AutomationUser" + System.currentTimeMillis();
    private static final String EMAIL_RANDOM = System.currentTimeMillis() + "@blankfactor.com";

    public static String create(CommonRequests commonRequests) {
        String customPayload = getCustomPayload();

        commonRequests.post(SERVICE_PATH, customPayload, false);
        CommonAssertions.verifyFullCreatedResponseAndSchema(RESPONSE_SCHEMA);
        CommonAssertions.validateFieldValue(UserResponse.TOKEN, USER_TOKEN);
        return USER_TOKEN;
    }

    private static String getCustomPayload() {
        String payload = EnvironmentProperties.getProperty(UserRequests.SERVICE_PAYLOAD);
        payload = payload.replace("USER_TOKEN", USER_TOKEN);
        payload = payload.replace("EMAIL_RANDOM", EMAIL_RANDOM);
        return payload;
    }
}
