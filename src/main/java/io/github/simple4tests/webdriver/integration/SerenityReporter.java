package io.github.simple4tests.webdriver.integration;

import io.github.simple4tests.webdriver.reporting.Reporter;
import net.thucydides.core.model.ReportData;
import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestResult;
import net.thucydides.core.model.TestStep;
import net.thucydides.core.steps.ExecutedStepDescription;
import net.thucydides.core.steps.StepEventBus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class SerenityReporter implements Reporter {

    private TestStep getCurrentStep() {
        TestOutcome outcome = StepEventBus.getEventBus().getBaseStepListener().getCurrentTestOutcome();
        return outcome.currentStep().isPresent() ?
                outcome.currentStep().get() :
                outcome.recordStep(TestStep.forStepCalled("Background").withResult(TestResult.SUCCESS)).currentStep().get();
    }

    @Override
    public void reportAction(String action) {
        StepEventBus.getEventBus().stepStarted(ExecutedStepDescription.withTitle(action));
        StepEventBus.getEventBus().stepFinished();
    }

    @Override
    public void reportData(String data) {
        getCurrentStep().recordReportData(ReportData.withTitle("DATA").andContents(data).asEvidence(false));
    }

    // Charset.forName(StandardCharsets.UTF_8.name());
    @Override
    public void reportData(Path path) {
        try {
            getCurrentStep().recordReportData(ReportData
                    .withTitle("DATA").fromFile(path, StandardCharsets.UTF_8).asEvidence(false));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reportCheck(String check) {
        StepEventBus.getEventBus().stepStarted(ExecutedStepDescription.withTitle(check));
        reportScreenshot();
        StepEventBus.getEventBus().stepFinished();
    }

    @Override
    public void reportError(String error) {
        errors.add(error);
        getCurrentStep().recordReportData(ReportData.withTitle("ERROR").andContents(error).asEvidence(true));
        getCurrentStep().setResult(TestResult.ERROR);
    }

    @Override
    public void reportError(Path path) {
        try {
            getCurrentStep().withReportData(ReportData
                    .withTitle("ERROR").fromFile(path, StandardCharsets.UTF_8).asEvidence(true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reportScreenshot() {
        StepEventBus.getEventBus().takeScreenshot();
    }
}
