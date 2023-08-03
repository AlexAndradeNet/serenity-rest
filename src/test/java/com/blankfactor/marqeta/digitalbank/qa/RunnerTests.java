/* BlankFactor (C)2023 */
package com.blankfactor.marqeta.digitalbank.qa;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = {"pretty"},
        glue = {"com.blankfactor.marqeta.digitalbank.qa.steps", "com.marqeta.api.commons"},
        features = "classpath:features")
public class RunnerTests {}
