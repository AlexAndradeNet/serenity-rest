/* BlankFactor (C)2023 */
package com.marqeta.api.commons;

import com.blankfactor.log.LoggerUtil;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

public class CommonRequests {

    @Step("POST {0} {1}")
    public void post(
            Credentials credentials,
            String servicePath,
            String servicePayload,
            boolean isPayloadAVariable) {

        if (isPayloadAVariable) {
            servicePayload = EnvironmentProperties.getProperty(servicePayload);
        }

        servicePayload = servicePayload.replace("\\\"", "\"");

        servicePath = EnvironmentProperties.getProperty(servicePath);

        String response =
                SerenityRest.rest()
                        .given()
                        .spec(ReusableSpecifications.getGenericRequestSpec())
                        .auth()
                        .preemptive()
                        .basic(credentials.getUsername(), credentials.getPassword())
                        .when()
                        .body(servicePayload)
                        .post(servicePath)
                        .then()
                        .extract()
                        .body()
                        .asString();

        LoggerUtil.info("POST " + servicePath + " " + servicePayload);
        LoggerUtil.info("RESPONSE " + response);
    }

    public void post(Credentials credentials, String servicePath, String servicePayload) {
        post(credentials, servicePath, servicePayload, true);
    }
}
