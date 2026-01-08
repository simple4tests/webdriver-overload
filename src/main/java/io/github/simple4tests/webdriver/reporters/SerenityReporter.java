package io.github.simple4tests.webdriver.reporters;

import net.serenitybdd.core.reports.AddReportContentEvent;
import net.serenitybdd.core.reports.ReportDataSaver;
import net.thucydides.core.steps.StepEventBus;
import net.thucydides.core.steps.events.StepFinishedEvent;
import net.thucydides.core.steps.events.StepStartedEvent;
import net.thucydides.core.steps.events.UpdateCurrentStepFailureCause;
import net.thucydides.core.steps.session.TestSession;
import net.thucydides.model.ThucydidesSystemProperty;
import net.thucydides.model.domain.ReportData;
import net.thucydides.model.screenshots.ScreenshotAndHtmlSource;
import net.thucydides.model.steps.ExecutedStepDescription;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
        screenshots
                .computeIfAbsent(TestSession.getTestSessionContext().getSessionId(), k -> new ArrayList<>())
                .addAll(TestSession.getTestSessionContext().getStepEventBus().takeScreenshots());
    }
}
