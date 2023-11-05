/* BlankFactor (C)2023 */
package com.marqeta.api.commons;

import com.blankfactor.Credentials;
import com.blankfactor.log.LoggerUtil;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

public class CommonRequests {

    @Step("POST {0} {1}")
    public void post(String servicePath, String servicePayload, boolean isPayloadAVariable) {

        if (isPayloadAVariable) {
            servicePayload = EnvironmentProperties.getProperty(servicePayload);
        }

        servicePayload = replaceUrlInPayload(servicePayload);

        servicePath = EnvironmentProperties.getProperty(servicePath);
        servicePath =
                servicePath.replace(
                        "BF_CORE_PUBLIC_URL", Credentials.getInstance().getBfBankPublicUrl());
        servicePath =
                servicePath.replace(
                        "BF_MARQETA_PUBLIC_URL", Credentials.getInstance().getBfMarqetaPublicUrl());

        String response =
                SerenityRest.rest()
                        .given()
                        .spec(ReusableSpecifications.getGenericRequestSpec())
                        .auth()
                        .preemptive()
                        .basic(
                                Credentials.getInstance().getUsername(),
                                Credentials.getInstance().getPassword())
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

    public void post(String servicePath, String servicePayload) {
        post(servicePath, servicePayload, true);
    }

    private String replaceUrlInPayload(String servicePayload) {
        servicePayload = servicePayload.replace("\\\"", "\"");
        servicePayload =
                servicePayload.replace(
                        "BF_CORE_PUBLIC_URL", Credentials.getInstance().getBfBankPublicUrl());

        servicePayload =
                servicePayload.replace(
                        "BF_MARQETA_PUBLIC_URL", Credentials.getInstance().getBfMarqetaPublicUrl());

        return servicePayload;
    }
}
