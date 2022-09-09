package io.github.simple4tests.webdriver.framework;

import io.cucumber.java.Scenario;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.nio.file.Path;

@NoArgsConstructor
@AllArgsConstructor
public class CucumberJavaReporter extends SystemOutReporter {

    public Scenario scenario;
    public WebDriver driver;

    public void init(Scenario scenario, WebDriver driver) {
        this.scenario = scenario;
        this.driver = driver;
    }

    @Override
    public void reportAction(String action) {
        scenario.attach("", "text/plain", "action : ".concat(action));
    }

    @Override
    public void reportData(String data) {
        scenario.attach("", "text/plain", "data : ".concat(data));
    }

    @Override
    public void reportData(Path path) {
        scenario.attach("", "text/plain", "data : ".concat(path.toAbsolutePath().toString()));
    }

    @Override
    public void reportCheck(String check) {
        scenario.attach("", "text/plain", "check : ".concat(check));
        reportScreenshot();
    }

    @Override
    public void reportError(String error) {
        super.reportError(error);
        scenario.attach("", "text/plain", "error : ".concat(error));
    }

    @Override
    public void reportError(Path path) {
        super.reportError(path);
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
