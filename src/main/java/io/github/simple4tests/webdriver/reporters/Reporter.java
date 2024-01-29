package io.github.simple4tests.webdriver.reporters;

import io.github.simple4tests.webdriver.utils.Groovy;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public interface Reporter {

    List<String> errors = new ArrayList<>();

    default boolean hasErrors() {
        return !errors.isEmpty();
    }

    default void addError(String error) {
        errors.add(error);
    }

    default void clearErrors() {
        errors.clear();
    }

    default String getErrorsSummary() {
        if (hasErrors()) {
            return String.join("\n", errors);
        }
        return "NO ERRORS FOUND, CONGRATULATIONS!";
    }

    default void throwAssertionErrorIfAny(boolean reportErrorsSummary) {
        if (hasErrors()) {
            if (reportErrorsSummary)
                reportAction("Report error(s) summary", getErrorsSummary());
            throw new AssertionError(String.format("%d error(s) found", errors.size()));
        }
    }

    default <T> void assertThat(final String check, final T actual, final T expected) {
        assertThat(check, actual, Matchers.equalTo(expected));
    }

    default <T> void assertThat(final String check, final T actual, final Matcher<? super T> expected) {
        startAction(check);
        reportScreenshot();
        try {
            MatcherAssert.assertThat("", actual, expected);
        } catch (AssertionError e) {
            reportError(getErrorDescription(check, String.format("Actual: %s%s", actual, e.getMessage())));
        }
        endAction();
    }

    default void groovyAssertThat(final String check, final String actual, final String expectedAsGroovyMatcher) {
        startAction(check);
        reportScreenshot();
        try {
            Groovy.getShell(Groovy.IMPORT_HAMCREST_MATCHERS)
                    .evaluate(String.format("assertThat '%s', %s", actual, expectedAsGroovyMatcher));
        } catch (Throwable t) {
            reportError(getErrorDescription(check, String.format("Actual: %s%s", actual, t.getMessage())));
        }
        endAction();
    }

    default void groovyAssert(final String check, final String groovyAssertExpression) {
        startAction(check);
        reportScreenshot();
        try {
            Groovy.getShell().evaluate(String.format("assert %s", groovyAssertExpression));
        } catch (Throwable t) {
            reportError(getErrorDescription(check, t.getMessage()));
        }
        endAction();
    }

    private String getErrorDescription(String check, String mismatch) {
        return String.format("%s ?\n%s\n", check, mismatch);
    }

    void startAction(String action);

    void endAction();

    void reportData(String data);

    void reportData(Path path);

    void reportError(String error);

    void reportError(Path path);

    void reportScreenshot();

    default void reportAction(String action) {
        startAction(action);
        endAction();
    }

    default void reportAction(String action, String data) {
        startAction(action);
        reportData(data);
        endAction();
    }

    default void reportCheck(String check) {
        startAction(check);
        reportScreenshot();
        endAction();
    }
}
