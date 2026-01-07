package io.github.simple4tests.webdriver.reporters;

import net.serenitybdd.core.reports.AddReportContentEvent;
import net.serenitybdd.core.reports.AddReportScreenshotEvent;
import net.serenitybdd.core.reports.ReportDataSaver;
import net.thucydides.core.steps.StepEventBus;
import net.thucydides.core.steps.events.StepFinishedEvent;
import net.thucydides.core.steps.events.StepStartedEvent;
import net.thucydides.core.steps.events.UpdateCurrentStepFailureCause;
import net.thucydides.core.steps.session.TestSession;
import net.thucydides.core.webdriver.SerenityWebdriverManager;
import net.thucydides.model.ThucydidesSystemProperty;
import net.thucydides.model.domain.ReportData;
import net.thucydides.model.steps.ExecutedStepDescription;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class SerenityReporter extends SystemOutReporter {

    public int screenshot_id = 0;

    @Override
    public void startStep(String step) {
        super.startStep(step);
        TestSession.addEvent(new StepStartedEvent(ExecutedStepDescription.withTitle(step)));
    }

    @Override
    public void endStep() {
        super.endStep();
        TestSession.addEvent(new StepFinishedEvent());
    }

    @Override
    public void reportData(String data) {
        super.reportData(data);
        StepEventBus eventBus = TestSession.getTestSessionContext().getStepEventBus();
        TestSession.addEvent(new AddReportContentEvent(
                new ReportDataSaver(eventBus),
                ReportData.withTitle("DATA").andContents(data)));
    }

    // Charset.forName(StandardCharsets.UTF_8.name());
    @Override
    public void reportData(Path path) {
        super.reportData(path);
        try {
            StepEventBus eventBus = TestSession.getTestSessionContext().getStepEventBus();
            Charset encoding = Charset.forName(ThucydidesSystemProperty.SERENITY_REPORT_ENCODING.from(eventBus.getEnvironmentVariables(), StandardCharsets.UTF_8.name()));
            TestSession.addEvent(new AddReportContentEvent(
                    new ReportDataSaver(eventBus),
                    ReportData.withTitle("DATA").fromFile(path, encoding)));
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public void reportError(String error) {
        super.reportError(error);
        TestSession.addEvent(new UpdateCurrentStepFailureCause(new Throwable(error)));
    }

    @Override
    public void reportError(Path path) {
        super.reportError(path);
        reportData(path);
        TestSession.addEvent(new UpdateCurrentStepFailureCause(new Throwable(path.getFileName().toString())));
    }

    @Override
    public void reportScreenshot() {
        createSerenityOutputDirectory();
        AddReportScreenshotEvent screenshotEvent = new AddReportScreenshotEvent(
                TestSession.getTestSessionContext().getSessionId() + ++screenshot_id,
                ((TakesScreenshot) SerenityWebdriverManager.inThisTestThread().getCurrentDriver())
                        .getScreenshotAs(OutputType.BYTES)
        );
        TestSession.addEvent(screenshotEvent);
    }

    private void createSerenityOutputDirectory() {
        try {
            Path outputDirectory = TestSession.getTestSessionContext().getStepEventBus().getBaseStepListener()
                    .getOutputDirectory().getAbsoluteFile().toPath();
            if (!Files.exists(outputDirectory))
                Files.createDirectories(outputDirectory);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
}
