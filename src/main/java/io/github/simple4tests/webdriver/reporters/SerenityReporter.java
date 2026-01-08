package io.github.simple4tests.webdriver.reporters;

import net.serenitybdd.core.reports.AddReportContentEvent;
import net.serenitybdd.core.reports.ReportDataSaver;
import net.thucydides.core.steps.StepEventBus;
import net.thucydides.core.steps.events.StepFinishedEvent;
import net.thucydides.core.steps.events.StepStartedEvent;
import net.thucydides.core.steps.events.UpdateCurrentStepFailureCause;
import net.thucydides.core.steps.session.TestSession;
import net.thucydides.core.webdriver.SerenityWebdriverManager;
import net.thucydides.model.domain.ReportData;
import net.thucydides.model.screenshots.ScreenshotAndHtmlSource;
import net.thucydides.model.steps.ExecutedStepDescription;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SerenityReporter extends SystemOutReporter {

    private final Map<String, List<ScreenshotAndHtmlSource>> screenshots = new HashMap<>();

    @Override
    public void startStep(String step) {
        super.startStep(step);
        TestSession.addEvent(new StepStartedEvent(ExecutedStepDescription.withTitle(step)));
    }

    @Override
    public void endStep() {
        super.endStep();
        String sessionId = TestSession.getTestSessionContext().getSessionId();
        if (screenshots.containsKey(sessionId)) {
            TestSession.addEvent(new StepFinishedEvent(screenshots.get(sessionId)));
            screenshots.remove(sessionId);
        } else {
            TestSession.addEvent(new StepFinishedEvent());
        }
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
            TestSession.addEvent(new AddReportContentEvent(
                    new ReportDataSaver(eventBus),
                    ReportData.withTitle("DATA").fromPath(path)));
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
        StepEventBus eventBus = TestSession.getTestSessionContext().getStepEventBus();
        if (takeScreenshotWhenStepFinished()) {
            String sessionId = TestSession.getTestSessionContext().getSessionId();
            screenshots.computeIfAbsent(sessionId, k -> new ArrayList<>())
                    .addAll(eventBus.takeScreenshots());
        } else {
            try {
                TestSession.addEvent(new AddReportContentEvent(
                        new ReportDataSaver(eventBus),
                        ReportData.withTitle("SCREENSHOT").fromPath(
                                ((TakesScreenshot) SerenityWebdriverManager.inThisTestThread().getCurrentDriver())
                                        .getScreenshotAs(OutputType.FILE).toPath()
                        )));
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
        }
    }

    private boolean takeScreenshotWhenStepFinished() {
        List<String> takeScreenshotAtStepFinished = List.of(
                "FOR_EACH_ACTION",
                "BEFORE_AND_AFTER_EACH_STEP",
                "AFTER_EACH_STEP"
        );
        String takeScreenshotsProperty = TestSession.getTestSessionContext().getStepEventBus()
                .getEnvironmentVariables().getProperty("serenity.take.screenshots");
        return null == takeScreenshotsProperty || takeScreenshotAtStepFinished.contains(takeScreenshotsProperty);
    }
}
