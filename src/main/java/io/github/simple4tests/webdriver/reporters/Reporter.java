package io.github.simple4tests.webdriver.reporters;

import io.github.simple4tests.webdriver.utils.Groovy;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import java.nio.file.Path;

public interface Reporter {

    private String formatError(String step, String mismatch) {
        return String.format("%s ?\n%s\n", step, mismatch);
    }

    default <T> void assertThat(final String step, final T actual, final T expected) {
        assertThat(step, actual, Matchers.equalTo(expected));
    }

    default <T> void assertThat(final String step, final T actual, final Matcher<? super T> expected) {
        startStep(step);
        reportScreenshot();
        try {
            MatcherAssert.assertThat("", actual, expected);
        } catch (AssertionError e) {
            reportError(formatError(step, String.format("Actual: %s%s", actual, e.getMessage())));
        }
        endStep();
    }

    default void groovyAssertThat(final String step, final String actual, final String expectedAsGroovyMatcher) {
        startStep(step);
        reportScreenshot();
        try {
            Groovy.getShell(Groovy.IMPORT_HAMCREST_MATCHERS)
                    .evaluate(String.format("assertThat '%s', %s", actual, expectedAsGroovyMatcher));
        } catch (Throwable t) {
            reportError(formatError(step, String.format("Actual: %s%s", actual, t.getMessage())));
        }
        endStep();
    }

    default void groovyAssert(final String step, final String groovyAssertExpression) {
        startStep(step);
        reportScreenshot();
        try {
            Groovy.getShell().evaluate(String.format("assert %s", groovyAssertExpression));
        } catch (Throwable t) {
            reportError(formatError(step, t.getMessage()));
        }
        endStep();
    }

    void startStep(String step);

    void endStep();

    void reportData(String data);

    void reportData(Path path);

    void reportError(String error);

    void reportError(Path path);

    void reportScreenshot();

    default void reportAction(String action) {
        startStep(action);
        endStep();
    }

    default void reportAction(String action, String data) {
        startStep(action);
        reportData(data);
        endStep();
    }
}
