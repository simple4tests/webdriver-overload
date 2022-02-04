package io.github.simple4tests.webdriver.utils;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Groovy {

    private static final ThreadLocal<GroovyShell> localGroovyShell = new ThreadLocal<>();

    private static final List<String> IMPORT_DEFAULT = new ArrayList<>();
    private static final List<String> IMPORT_ASSERTIONS = List.of("org.hamcrest.MatcherAssert", "org.hamcrest.Matchers");

    private static List<String> currentImports;

    private static void resetShell() {
        localGroovyShell.set(null);
    }

    private static GroovyShell initShell(final List<String> classNames) {
        currentImports = classNames;

        final ImportCustomizer importCustomizer = new ImportCustomizer();
        for (String className : classNames) {
            importCustomizer.addStaticStars(className);
        }
        final CompilerConfiguration configuration = new CompilerConfiguration();
        configuration.addCompilationCustomizers(importCustomizer);

        return new GroovyShell(Groovy.class.getClassLoader(), new Binding(), configuration);
    }

    private static GroovyShell getShell(final List<String> classNames) {
        if (Objects.isNull(localGroovyShell.get())) {
            localGroovyShell.set(initShell(classNames));
        } else if (!currentImports.stream().sorted().equals(classNames.stream().sorted())) {
            resetShell();
            localGroovyShell.set(initShell(classNames));
        }
        return localGroovyShell.get();
    }

    public static String evaluate(final List<String> classNames, final String script) {
        try {
            getShell(classNames).evaluate(script);
            return "OK";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public static String evaluate(final String script) {
        return evaluate(IMPORT_DEFAULT, script);
    }

    public static String evalAssertion(String script) {
        try {
            getShell(IMPORT_ASSERTIONS).evaluate(script);
            return "OK";
        } catch (AssertionError e) {
            return e.getMessage();
        } catch (Exception e) {
            return "Groovy exception: ".concat(e.getMessage());
        }
    }

    // Eval HamcrestAssertion
    public static String evalAssertThat(String actual, String expected) {
        return evalAssertion(String.format("assertThat '%s', %s", actual, expected));
    }

    // Eval GroovyAssertion/PowerAssertion
    public static String evalAssert(String assertExpression) {
        return evalAssertion("assert ".concat(assertExpression));
    }
}
