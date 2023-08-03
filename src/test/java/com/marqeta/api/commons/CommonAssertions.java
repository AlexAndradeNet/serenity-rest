/* BlankFactor (C)2023 */
package com.marqeta.api.commons;

import static net.serenitybdd.rest.SerenityRest.restAssuredThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesRegex;

import io.restassured.module.jsv.JsonSchemaValidator;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import org.apache.http.HttpStatus;

public class CommonAssertions {

    private static final String GUID_REGEX =
            "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";

    @Step("Verify response code 200")
    private void shouldSeeSuccessfulStatusCode() {
        restAssuredThat(response -> response.statusCode(HttpStatus.SC_OK));
    }

    @Step("Verify response code 201 - Created")
    private void shouldSeeCreatedStatusCode() {
        restAssuredThat(response -> response.statusCode(HttpStatus.SC_CREATED));
    }

    @Step("Verify successful response and Schema")
    public void verifyFullSuccessResponseAndSchema(String schemaToVerify) {
        shouldSeeSuccessfulStatusCode();
        validateJSONSchema(schemaToVerify);
    }

    @Step("Verify successful created response and Schema")
    public void verifyFullCreatedResponseAndSchema(String schemaToVerify) {
        shouldSeeCreatedStatusCode();
        validateJSONSchema(schemaToVerify);
    }

    @Step("Verify response schema")
    private void validateJSONSchema(String schemaPath) {
        String finalSchemaPath = EnvironmentProperties.getProperty(schemaPath);
        restAssuredThat(
                response ->
                        response.body(
                                        JsonSchemaValidator.matchesJsonSchemaInClasspath(
                                                finalSchemaPath))
                                .assertThat());
    }

    @Step("Verify that the response contains a token and its is a valid GUID")
    public String validateIfTheTokenIsAGuidAndGetIt(String responseField) {
        restAssuredThat(response -> response.body(responseField, matchesRegex(GUID_REGEX)));
        return SerenityRest.then().extract().path(responseField);
    }

    @Step("Verify that the response contains the field '{0}' and its value is '{1}'")
    public void validateFieldValue(String responseField, String value) {
        restAssuredThat(response -> response.body(responseField, equalTo(value)));
    }
}
