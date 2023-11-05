/* BlankFactor (C)2023 */
package com.blankfactor.marqeta.digitalbank.qa.steps;

import com.blankfactor.enums.TransactionStateEnum;
import com.blankfactor.enums.TransactionTypeEnum;
import com.blankfactor.marqeta.digitalbank.qa.api.webhook.WebhookAssertions;
import com.blankfactor.marqeta.digitalbank.qa.api.webhook.WebhookRequests;
import com.marqeta.api.simulate.reversal.ReversalAssertions;
import com.marqeta.api.simulate.reversal.ReversalRequests;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;

public class ReversalStepDefinitions extends CommonMethodsForClearingAndReverse {
    @Steps private ReversalRequests reversalRequests;
    @Steps private ReversalAssertions reversalAssertions;
    @Steps private WebhookRequests webhookRequests;
    @Steps private WebhookAssertions webhookAssertions;

    private String transactionToken;

    @Given("I am a user with an authorized transaction to reverse")
    public void iAmAUserWithAnAuthorizedTransaction() {
        transactionToken = createTransactionAccountAndAuthorize();
    }

    @When("I reverse the transaction")
    public void reverseTheTransaction() {
        reversalRequests.reverse(transactionToken);
    }

    @Then("the response of reversal is {transactionStateEnum}")
    public void theResponseOfReversingIs(TransactionStateEnum transactionStateEnum) {
        reversalAssertions.verifyState(transactionStateEnum);
    }

    @Given("I am a user with a transaction that has been reversed")
    public void iAmAUserWithATransactionThatHasBeenReversed() {
        iAmAUserWithAnAuthorizedTransaction();
        reverseTheTransaction();
        theResponseOfReversingIs(TransactionStateEnum.CLEARED);
    }

    @When("I mimic reversal the transaction")
    public void iMimicReversalTheTransaction() {
        webhookRequests.execute(TransactionTypeEnum.REVERSAL, authorizationDTO);
    }

    @Then("the reversal response from BF bank is successful")
    public void theReversalResponseFromBFBankIsSuccessful() {
        webhookAssertions.verifyFullResponse();
    }
}
