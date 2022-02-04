package io.github.simple4tests.webdriver.reporting;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import io.github.simple4tests.webdriver.utils.Groovy;

import java.util.ArrayList;
import java.util.List;

public interface Reporter {

    List<String> errors = new ArrayList<>();

    default void clearErrors() {
        errors.clear();
    }

    default void reportErrors() {
        if (0 < errors.size()) {
            addStep("ERRORS LIST");
            addStepEvidence("ERROR(S)", formatedErrors());
        }
    }

    default boolean hasErrors() {
        return 0 < errors.size();
    }

    private String formatErrorDescription(String checkName, String errorDescription) {
        return checkName.concat(" ? \n").concat(errorDescription);
    }

    private String formatedErrors() {
        StringBuilder formatedErrors = new StringBuilder();
        for (String error : errors) {
            formatedErrors.append(error.concat("\n"));
        }
        return formatedErrors.toString();
    }

    default <T> void addCheck(final String checkName, final T actual, final Matcher<? super T> expected) {
        addStep(checkName);
        takeScreenshot();
        addCheckData(checkName, actual, expected);
    }

    default <T> void addCheckData(final String checkName, final T actual, final Matcher<? super T> expected) {
        if (!expected.matches(actual)) {
            final Description description = new StringDescription();
            description
                    .appendText("Expected: ")
                    .appendDescriptionOf(expected)
                    .appendText("\tbut: ");
            expected.describeMismatch(actual, description);
            addStepEvidence(checkName.concat(" KO"), description.toString());
            errors.add(formatErrorDescription(checkName, description.toString()));
        }
    }

    default void addGroovyAssertThat(final String checkName, final String actual, final String expected) {
        addStep(checkName);
        takeScreenshot();
        addGroovyAssertThatCheckData(checkName, actual, expected);
    }

    default void addGroovyAssertThatCheckData(final String checkName, final String actual, final String expected) {
        String evalAssertThat = Groovy.evalAssertThat(actual, expected).replaceAll("\r\n", "");
        if (!evalAssertThat.equals("OK")) {
            addStepEvidence(checkName.concat(" KO"), evalAssertThat);
            errors.add(formatErrorDescription(checkName, evalAssertThat));
        }
    }

    default void addGroovyAssert(final String checkName, final String assertExpression) {
        addStep(checkName);
        takeScreenshot();
        addGroovyAssertCheckData(checkName, assertExpression);
    }

    default void addGroovyAssertCheckData(final String checkName, final String assertExpression) {
        String evalAssert = Groovy.evalAssert(assertExpression).replaceAll("\r\n", "");
        if (!evalAssert.equals("OK")) {
            addStepEvidence(checkName.concat(" KO"), evalAssert);
            errors.add(formatErrorDescription(checkName, evalAssert));
        }
    }

    void addStep(String stepDescription);

    void addStepData(String title, String stepData);

    void addStepEvidence(String title, String stepData);

    void takeScreenshot();
}
