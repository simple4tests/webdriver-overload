package io.github.simple4tests.webdriver.reporters;

import io.cucumber.java8.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.nio.file.Path;

public class CucumberJava8Reporter extends ReporterWithErrorHandler {

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
    public void endStep() {
    }

    @Override
    public void reportData(String data) {
        scenario.attach(data, "text/plain", "data");
    }

    @Override
    public void reportData(Path path) {
        reportData(path.toAbsolutePath().toString());
    }

    @Override
    public void reportError(String error) {
        addError(error);
        scenario.attach(error, "text/plain", "error");
    }

    @Override
    public void reportError(Path path) {
        addError(path.toAbsolutePath().toString());
        reportError(path.toAbsolutePath().toString());
    }

    @Override
    public void reportScreenshot() {
        scenario.attach(
                ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES),
                "image/png",
                "screenshot");
    }
}
