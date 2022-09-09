package io.github.simple4tests.webdriver.framework;

import java.nio.file.Path;

public class SystemOutReporter extends DummyReporter {

    private void printResult(String description) {
        System.out.println(description);
    }

    @Override
    public void reportAction(String action) {
        printResult("action : ".concat(action));
    }

    @Override
    public void reportData(String data) {
        printResult(String.format("data : \n%s", data));
    }

    @Override
    public void reportData(Path path) {
        printResult(String.format("data : \n%s", path.toAbsolutePath()));
    }

    @Override
    public void reportCheck(String check) {
        printResult("check : ".concat(check));
        reportScreenshot();
    }

    @Override
    public void reportError(String error) {
        super.reportError(error);
        printResult(String.format("error : %s", error));
    }

    @Override
    public void reportError(Path path) {
        super.reportError(path);
        printResult(String.format("error : %s", path.toAbsolutePath()));
    }

    @Override
    public void reportScreenshot() {
    }
}
