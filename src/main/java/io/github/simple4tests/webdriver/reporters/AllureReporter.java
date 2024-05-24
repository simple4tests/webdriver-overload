package io.github.simple4tests.webdriver.reporters;


import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StatusDetails;
import io.qameta.allure.model.StepResult;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class AllureReporter extends SystemOutReporter {

    public WebDriver driver;

    public void init(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void startStep(String step) {
        super.startStep(step);
        Allure.getLifecycle().startStep(
                UUID.randomUUID().toString(),
                (new StepResult()).setName(step).setStatus(Status.PASSED));
    }

    @Override
    public void endStep() {
        super.endStep();
        Allure.getLifecycle().stopStep();
    }

    @Override
    public void reportData(String data) {
        super.reportData(data);
        Allure.getLifecycle().updateStep((stepResult) ->
                stepResult.setStatusDetails(new StatusDetails().setMessage("DATA").setTrace(data)));
    }

    // Charset.forName(StandardCharsets.UTF_8.name());
    @Override
    public void reportData(Path path) {
        super.reportData(path);
        try {
            Allure.getLifecycle().addAttachment(
                    "DATA ".concat(path.getFileName().toString()),
                    null,
                    null,
                    Files.newInputStream(path));
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public void reportError(String error) {
        super.reportError(error);
        Allure.getLifecycle().updateStep((stepResult) ->
                stepResult.setStatusDetails(new StatusDetails().setMessage("ERROR").setTrace(error)));
        Allure.getLifecycle().updateStep((stepResult) -> stepResult.setStatus(Status.FAILED));
    }

    @Override
    public void reportError(Path path) {
        super.reportError(path);
        try {
            Allure.getLifecycle().addAttachment(
                    "ERROR ".concat(path.getFileName().toString()),
                    null,
                    null,
                    Files.newInputStream(path));
            Allure.getLifecycle().updateStep((stepResult) -> stepResult.setStatus(Status.FAILED));
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public void reportScreenshot() {
        Allure.getLifecycle().addAttachment(
                "screenshot",
                null,
                null,
                ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
    }
}
