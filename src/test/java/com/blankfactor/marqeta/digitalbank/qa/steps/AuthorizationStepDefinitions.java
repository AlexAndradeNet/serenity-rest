/* BlankFactor (C)2023 */
package com.blankfactor.marqeta.digitalbank.qa.steps;

import com.blankfactor.marqeta.digitalbank.qa.api.account.AccountRequests;
import com.marqeta.api.card.CardRequests;
import com.marqeta.api.commons.Credentials;
import com.marqeta.api.fundingsources.ProgramGatewayRequests;
import com.marqeta.api.product.ProductRequests;
import com.marqeta.api.simulate.authorization.AuthorizationRequests;
import com.marqeta.api.user.UserRequests;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.net.URISyntaxException;
import net.thucydides.core.annotations.Steps;

public class AuthorizationStepDefinitions {

    @Steps private ProgramGatewayRequests programGatewayRequests;
    @Steps private ProductRequests productRequests;
    @Steps private UserRequests userRequests;
    @Steps private CardRequests cardRequests;
    @Steps private AccountRequests accountRequests;
    @Steps private AuthorizationRequests authorizationRequests;

    private static final Credentials credentials = new Credentials();
    private static String cardToken;

    @Given("I am a user with a valid credit card")
    public void iAmAUserWithAValidCreditCard() throws URISyntaxException {
        String fundingToken = programGatewayRequests.createFundingSource(credentials);
        String productToken = productRequests.create(credentials, fundingToken);
        String userToken = userRequests.create(credentials);
        cardToken = cardRequests.create(credentials, userToken, productToken);
        accountRequests.create(credentials, cardToken);
    }

    @When("I simulate an authorization")
    public void iSimulateAnAuthorization() {
        String authorizationToken = authorizationRequests.authorize(credentials, cardToken);
    }

    @Then("the response of transaction is {word}")
    public void getAResponseOfTransactionAsPENDING(String transactionState) {
        authorizationRequests.verifyTransactionState(transactionState);
    }
}
