package io.github.simple4tests.webdriver.reporting;

import java.nio.file.Path;

public class DummyReporter implements Reporter {

    @Override
    public void reportAction(String action) {
    }

    @Override
    public void reportData(String data) {
    }

    @Override
    public void reportData(Path path) {
    }

    @Override
    public void reportCheck(String check) {
        reportScreenshot();
    }

    @Override
    public void reportError(String error) {
        errors.add(error);
    }

    @Override
    public void reportError(Path path) {
        errors.add(path.toAbsolutePath().toString());
    }

    @Override
    public void reportScreenshot() {
    }
}
