package io.github.simple4tests.webdriver.framework;

import io.cucumber.java8.Scenario;
import lombok.NoArgsConstructor;

import java.nio.file.Path;

@NoArgsConstructor
public class CucumberJava8Reporter extends SystemOutReporter {

    public Scenario scenario;

    @Override
    public void reportAction(String action) {
        scenario.attach(action, "text/html", "Test action");
    }

    @Override
    public void reportData(String data) {
        scenario.attach(data, "text/html", "Test data");
    }

    @Override
    public void reportData(Path path) {
        scenario.attach(path.toAbsolutePath().toString(), "text/plain", "Test data");
    }

    @Override
    public void reportCheck(String check) {
        scenario.attach(check, "text/html", "Test check");
        reportScreenshot();
    }

    @Override
    public void reportError(String error) {
        super.reportError(error);
        scenario.attach(error, "text/html", "Test error");
    }

    @Override
    public void reportError(Path path) {
        super.reportError(path);
        scenario.attach(path.toAbsolutePath().toString(), "text/plain", "Test error");
    }

    @Override
    public void reportScreenshot() {
    }
}
