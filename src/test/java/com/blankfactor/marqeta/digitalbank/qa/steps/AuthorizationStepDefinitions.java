/* BlankFactor (C)2023 */
package com.blankfactor.marqeta.digitalbank.qa.steps;

import com.blankfactor.marqeta.digitalbank.qa.api.account.AccountCreateRequests;
import com.marqeta.api.card.CardRequests;
import com.marqeta.api.commons.CommonRequests;
import com.marqeta.api.fundingsources.ProgramGatewayRequests;
import com.marqeta.api.product.ProductRequests;
import com.marqeta.api.simulate.authorization.AuthorizationRequests;
import com.marqeta.api.user.UserRequests;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.net.URISyntaxException;

public class AuthorizationStepDefinitions {

    private static final CommonRequests commonRequests = new CommonRequests();
    private static String cardToken;

    @Given("I am a user with a valid credit card")
    public void iAmAUserWithAValidCreditCard() throws URISyntaxException {

        String fundingToken = ProgramGatewayRequests.createFundingSource(commonRequests);
        String productToken = ProductRequests.create(commonRequests, fundingToken);
        String userToken = UserRequests.create(commonRequests);
        cardToken = CardRequests.create(commonRequests, userToken, productToken);
        AccountCreateRequests.create(cardToken);
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
