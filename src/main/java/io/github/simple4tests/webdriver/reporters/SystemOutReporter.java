package io.github.simple4tests.webdriver.reporters;

import java.nio.file.Path;

public class SystemOutReporter implements Reporter {

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
    public void reportAction(String action, String data) {
        printResult("action : ".concat(action));
        printResult(String.format("data : \n%s", data));
    }

    @Override
    public void reportData(Path path) {
        printResult(String.format("data : \n%s", path.toAbsolutePath()));
    }

    @Override
    public void reportCheck(String check) {
        printResult("check : ".concat(check));
    }

    @Override
    public void reportError(String error) {
        errors.add(error);
        printResult(String.format("error : %s", error));
    }

    @Override
    public void reportError(Path path) {
        errors.add(path.toAbsolutePath().toString());
        printResult(String.format("error : %s", path.toAbsolutePath()));
    }

    @Override
    public void reportScreenshot() {
    }
}
