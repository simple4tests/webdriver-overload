package io.github.simple4tests.webdriver.reporters;

import net.thucydides.core.steps.StepEventBus;
import net.thucydides.model.domain.ReportData;
import net.thucydides.model.domain.TestOutcome;
import net.thucydides.model.domain.TestResult;
import net.thucydides.model.domain.TestStep;
import net.thucydides.model.steps.ExecutedStepDescription;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class SerenityReporter extends SystemOutReporter {

    private TestStep getCurrentStep() {
        TestOutcome outcome = StepEventBus.getEventBus().getBaseStepListener().getCurrentTestOutcome();
        return outcome.currentStep().isPresent() ?
                outcome.currentStep().get() :
                outcome.recordStep(TestStep.forStepCalled("Background").withResult(TestResult.SUCCESS)).currentStep().get();
    }

    @Override
    public void reportAction(String action) {
        super.reportAction(action);
        StepEventBus.getEventBus().stepStarted(ExecutedStepDescription.withTitle(action));
        StepEventBus.getEventBus().stepFinished();
    }

    @Override
    public void reportData(String data) {
        super.reportData(data);
        getCurrentStep().recordReportData(ReportData.withTitle("DATA").andContents(data).asEvidence(false));
    }

    // Charset.forName(StandardCharsets.UTF_8.name());
    @Override
    public void reportData(Path path) {
        super.reportData(path);
        try {
            getCurrentStep().recordReportData(ReportData.withTitle("DATA").fromFile(path, StandardCharsets.UTF_8).asEvidence(false));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reportAction(String action, String data) {
        reportAction(action);
        reportData(data);
    }

    @Override
    public void reportCheck(String check) {
        super.reportCheck(check);
        StepEventBus.getEventBus().stepStarted(ExecutedStepDescription.withTitle(check));
        reportScreenshot();
        StepEventBus.getEventBus().stepFinished();
    }

    @Override
    public void reportError(String error) {
        super.reportError(error);
        getCurrentStep().recordReportData(ReportData.withTitle("ERROR").andContents(error).asEvidence(true));
        getCurrentStep().setResult(TestResult.ERROR);
    }

    @Override
    public void reportError(Path path) {
        super.reportError(path);
        try {
            getCurrentStep().recordReportData(ReportData.withTitle("ERROR").fromFile(path, StandardCharsets.UTF_8).asEvidence(true));
            getCurrentStep().setResult(TestResult.ERROR);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reportScreenshot() {
        StepEventBus.getEventBus().takeScreenshot();
    }
}
