package io.github.simple4tests.webdriver.reporters;

import java.nio.file.Path;

public class SystemOutReporter implements Reporter {

    private void printResult(String description) {
        System.out.println(description);
    }

    @Override
    public void startStep(String step) {
        printResult("step : ".concat(step));
    }

    @Override
    public void endStep() {
    }

    @Override
    public void reportData(String data) {
        printResult(String.format("data : %s", data));
    }

    @Override
    public void reportData(Path path) {
        printResult(String.format("data : %s", path.toAbsolutePath()));
    }

    @Override
    public void reportError(String error) {
        addError(error);
        printResult(String.format("error : %s", error));
    }

    @Override
    public void reportError(Path path) {
        addError(path.toAbsolutePath().toString());
        printResult(String.format("error : %s", path.toAbsolutePath()));
    }

    @Override
    public void reportScreenshot() {
    }
}
