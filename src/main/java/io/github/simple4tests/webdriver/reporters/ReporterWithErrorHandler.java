package io.github.simple4tests.webdriver.reporters;

import java.util.ArrayList;
import java.util.List;

public abstract class ReporterWithErrorHandler implements Reporter {

    List<String> errors = new ArrayList<>();

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public void addError(String error) {
        errors.add(error);
    }

    public void clearErrors() {
        errors.clear();
    }

    public String getErrorsSummary() {
        if (hasErrors()) {
            return String.join("\n", errors);
        }
        return "NO ERRORS FOUND, CONGRATULATIONS!";
    }

    public void throwAssertionErrorIfAny(boolean reportErrorsSummary) {
        if (hasErrors()) {
            if (reportErrorsSummary)
                reportAction("Report error(s) summary", getErrorsSummary());
            throw new AssertionError(String.format("%d error(s) found", errors.size()));
        }
    }
}
