package cucumber.service;

import com.cucumber.listener.Reporter;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

/**
 * Created by Jianhua Sun on 2018/9/6.
 */
public class ServiceHook {
    @Before
    public void cucumberBefore() {
        Reporter.assignAuthor("xxxx");
    }

    @After
    public void cucumberAfter(Scenario scenario) {
        if (scenario.isFailed()) {
            // TODO. Capture and embed screenshot in test reports.
            String screenshotName = scenario.getName().replaceAll(" ", "_");

        }
    }
}
