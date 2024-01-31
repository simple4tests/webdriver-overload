package io.github.simple4tests.webdriver.reporters;

import io.cucumber.java.Scenario;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.nio.file.Path;

@NoArgsConstructor
@AllArgsConstructor
public class CucumberJavaReporter implements Reporter {

    public Scenario scenario;
    public WebDriver driver;

    public void init(Scenario scenario, WebDriver driver) {
        this.scenario = scenario;
        this.driver = driver;
    }

    @Override
    public void startStep(String step) {
        scenario.attach("", "text/plain", "step : ".concat(step));
    }

    @Override
    public void endStep() {}

    @Override
    public void reportData(String data) {
        scenario.attach("", "text/plain", "data : ".concat(data));
    }

    @Override
    public void reportData(Path path) {
        scenario.attach("", "text/plain", "data : ".concat(path.toAbsolutePath().toString()));
    }

    @Override
    public void reportError(String error) {
        addError(error);
        scenario.attach("", "text/plain", "error : ".concat(error));
    }

    @Override
    public void reportError(Path path) {
        addError(path.toAbsolutePath().toString());
        scenario.attach("", "text/plain", "error : ".concat(path.toAbsolutePath().toString()));
    }

    @Override
    public void reportScreenshot() {
        scenario.attach(
                ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES),
                "image/png",
                "screenshot");
    }
}
