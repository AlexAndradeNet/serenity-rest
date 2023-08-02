/* BlankFactor (C)2023 */
package com.blankfactor.marqeta.digitalbank.qa;

import static org.junit.Assert.assertTrue;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = {"pretty"},
        features = "classpath:features")
public class RunnerTests {
    @Test
    public void test() {
        // Empty test to run Cucumber
        System.out.println("************************* Running Cucumber tests");
        assertTrue(true);
    }
}
