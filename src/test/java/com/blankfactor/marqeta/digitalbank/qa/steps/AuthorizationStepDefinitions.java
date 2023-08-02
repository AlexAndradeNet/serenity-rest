/* BlankFactor (C)2023 */
package com.blankfactor.marqeta.digitalbank.qa.steps;

import com.marqeta.api.card.CardRequests;
import com.marqeta.api.commons.CommonRequests;
import com.marqeta.api.fundingsources.ProgramGatewayRequests;
import com.marqeta.api.product.ProductRequests;
import com.marqeta.api.simulate.authorization.AuthorizationRequests;
import com.marqeta.api.user.UserRequests;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AuthorizationStepDefinitions {

    private static final CommonRequests commonRequests = new CommonRequests();
    private static String cardToken;

    @Given("I am a user with a valid credit card")
    public void iAmAUserWithAValidCreditCard() {

        String fundingToken = ProgramGatewayRequests.createFundingSource(commonRequests);
        String productToken = ProductRequests.create(commonRequests, fundingToken);
        String userToken = UserRequests.CreateUser(commonRequests);
        cardToken = CardRequests.create(commonRequests, userToken, productToken);
        // TODO: Add code to store the cardToken in a file or database
    }

    @When("I simulate an authorization")
    public void iSimulateAnAuthorization() {
        String authorizationToken = AuthorizationRequests.authorize(commonRequests, cardToken);
    }

    @Then("the response of transaction is {word}")
    public void getAResponseOfTransactionAsPENDING(String transactionState) {
        AuthorizationRequests.verifyTransactionState(transactionState);
    }
}
