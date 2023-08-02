/* BlankFactor (C)2023 */
package com.marqeta.api.commons;

import io.github.cdimascio.dotenv.Dotenv;
import net.serenitybdd.rest.SerenityRest;

public class CommonRequests {
    private static String username;
    private static String password;

    public CommonRequests() {
        // Create a Dotenv object and load the .env file
        Dotenv dotenv = Dotenv.configure().load();

        username = dotenv.get("MARQETA_USERNAME");
        password = dotenv.get("MARQETA_PASSWORD");
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public void post(String servicePath, String servicePayload, boolean isPayloadAVariable) {

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
                        .basic(username, password)
                        .when()
                        .body(servicePayload)
                        .post(servicePath)
                        .then()
                        .extract()
                        .body()
                        .asString();

        System.out.println("POST " + servicePath + " " + servicePayload);
        System.out.println("RESPONSE " + response);
    }

    public void post(String servicePath, String servicePayload) {
        post(servicePath, servicePayload, true);
    }
}
