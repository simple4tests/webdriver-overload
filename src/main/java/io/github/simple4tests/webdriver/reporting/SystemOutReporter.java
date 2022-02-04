package io.github.simple4tests.webdriver.reporting;

public class SystemOutReporter implements Reporter {

    @Override
    public void addStep(String stepDescription) {
        System.out.println("Test Step : ".concat(stepDescription));
    }

    @Override
    public void addStepData(String title, String stepData) {
        System.out.printf("Test data '%s': \n", title);
        System.out.println(stepData.concat("\n"));
    }

    @Override
    public void addStepEvidence(String title, String stepEvidence) {
        System.out.printf("Test error '%s': \n", title);
        System.out.println(stepEvidence.concat("\n"));
    }

    @Override
    public void takeScreenshot() {
    }
}
