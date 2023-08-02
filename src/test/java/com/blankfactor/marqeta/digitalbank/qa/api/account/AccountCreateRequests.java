/* BlankFactor (C)2023 */
package com.blankfactor.marqeta.digitalbank.qa.api.account;

import com.marqeta.api.commons.EnvironmentProperties;
import java.net.URI;
import java.net.URISyntaxException;
import net.serenitybdd.rest.SerenityRest;
import org.apache.http.HttpStatus;

public class AccountCreateRequests {

    private static final String SERVICE_PATH = "blankfactor.account.create";

    public static void create(String cardToken) throws URISyntaxException {
        URI uri = new URI(EnvironmentProperties.getProperty(SERVICE_PATH) + cardToken);
        SerenityRest.rest().when().put(uri).then().assertThat().statusCode(HttpStatus.SC_CREATED);

        System.out.println("PUT " + uri);
        System.out.println(
                "RESPONSE " + SerenityRest.lastResponse().headers().getValue("location"));
    }
}
