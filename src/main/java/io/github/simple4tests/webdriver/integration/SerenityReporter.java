package io.github.simple4tests.webdriver.integration;

import io.github.simple4tests.webdriver.reporting.Reporter;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;

import java.io.IOException;
import java.nio.file.Path;

public class SerenityReporter implements Reporter {

    @Override
    @Step("{0}")
    public void addStep(String stepDescription) {
    }

    @Override
    public void addStepData(String title, String stepData) {
        Serenity.recordReportData().withTitle(title).andContents(stepData);
    }

    public void addStepData(String title, Path stepData) {
        try {
            Serenity.recordReportData().withTitle(title).fromFile(stepData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addStepEvidence(String title, String stepData) {
        Serenity.recordReportData().asEvidence().withTitle(title).andContents(stepData);
    }

    public void addStepEvidence(String title, Path stepData) {
        try {
            Serenity.recordReportData().asEvidence().withTitle(title).fromFile(stepData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void takeScreenshot() {
        Serenity.takeScreenshot();
    }
}
