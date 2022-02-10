package io.github.simple4tests.webdriver.utils;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

import java.util.List;
import java.util.Objects;

public class Groovy {

    private static final ThreadLocal<GroovyShell> localGroovyShell = new ThreadLocal<>();

    public static final List<String> IMPORT_EMPTY = List.of();
    public static final List<String> IMPORT_HAMCREST_MATCHERS = List.of("org.hamcrest.MatcherAssert", "org.hamcrest.Matchers");

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

    public static Object evaluate(final List<String> classNames, final String script) {
        try {
            Object evaluate = getShell(classNames).evaluate(script);
            return null == evaluate ? "" : evaluate;
        } catch (Throwable t) {
            return t.getMessage();
        }
    }

    public static Object evaluate(final String script) {
        return evaluate(IMPORT_EMPTY, script);
    }
}
