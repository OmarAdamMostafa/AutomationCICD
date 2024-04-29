package Cucumber;

// To run testng code
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features="src/test/java/Cucumber", glue="PersonalProjects.StepDefinitions", 
monochrome=true, plugin= {"html:target/cucumber.html"}, tags="@ErrorValidation")
public class TestNGTestRunner extends AbstractTestNGCucumberTests{
	
}
