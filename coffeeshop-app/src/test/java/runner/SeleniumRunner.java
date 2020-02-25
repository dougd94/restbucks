package runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions(features="seleniumfeatures",glue="seleniumStepDefinitions")
public class SeleniumRunner {
	
}