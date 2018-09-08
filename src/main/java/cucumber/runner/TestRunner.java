package cucumber.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.utils.ConfigFileReader;
import gherkin.formatter.Reporter;
import org.testng.annotations.*;

import java.io.File;

/**
 * Created by Jianhua Sun on 2018/9/6.
 */

@CucumberOptions(
        features = "features",
        glue = {"cucumber/stepdefinition"},
        tags = {"~Ignore"},
        plugin = {"com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html"},
        monochrome = true
)
public class TestRunner {
    private TestNGCucumberRunner testNGCucumberRunner;

    public void beforeClass() {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    public void feature(CucumberFeatureWrapper cucumberFeatureWrapper) {
        testNGCucumberRunner.runCucumber(cucumberFeatureWrapper.getCucumberFeature());
    }

    public Object[][] features() {
        return testNGCucumberRunner.provideFeatures();
    }

    public void afterClass() {
        com.cucumber.listener.Reporter.loadXMLConfig(new File(new ConfigFileReader().getReportConfigPath()));
        testNGCucumberRunner.finish();
    }
}
