package io.github.simple4tests.webdriver.framework;

import java.nio.file.Path;

public class ReporterSystemOut implements io.github.simple4tests.webdriver.framework.Reporter {

    private void printResult(String description) {
        System.out.println(description);
    }

    @Override
    public void reportAction(String action) {
        printResult("Test action : ".concat(action));
    }

    @Override
    public void reportData(String data) {
        printResult(String.format("Test data : \n%s", data));
    }

    @Override
    public void reportData(Path path) {
        printResult(String.format("Test data : \n%s", path.toAbsolutePath()));
    }

    @Override
    public void reportCheck(String check) {
        printResult("Test check : ".concat(check));
        reportScreenshot();
    }

    @Override
    public void reportError(String error) {
        errors.add(error);
        printResult(String.format("Test error : %s", error));
    }

    @Override
    public void reportError(Path path) {
        errors.add(path.toAbsolutePath().toString());
        printResult(String.format("Test error : %s", path.toAbsolutePath()));
    }

    @Override
    public void reportScreenshot() {
    }
}
