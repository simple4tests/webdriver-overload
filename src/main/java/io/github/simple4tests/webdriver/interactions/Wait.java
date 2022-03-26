package io.github.simple4tests.webdriver.interactions;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.Collection;
import java.util.function.Function;

public class Wait {

    private final WebDriver driver;
    private Duration interval;
    private Duration timeout;
    private Collection<Class<? extends Throwable>> ignoredExceptions;
    private boolean ignoreTimeoutException;

    public Wait(WebDriver driver, Duration interval, Duration timeout, Collection<Class<? extends Throwable>> ignoredExceptions) {
        this.driver = driver;
        this.interval = interval;
        this.timeout = timeout;
        this.ignoredExceptions = ignoredExceptions;
        this.ignoreTimeoutException = false;
    }

    public Wait pollingEvery(Duration interval) {
        this.interval = interval;
        return this;
    }

    public Wait withTimeout(Duration timeout) {
        this.timeout = timeout;
        return this;
    }

    public Wait ignoreAll(Collection<Class<? extends Throwable>> ignoredExceptions) {
        this.ignoredExceptions = ignoredExceptions;
        return this;
    }

    public Wait ignoreTimeoutException() {
        ignoreTimeoutException = true;
        return this;
    }

    public <T> T until(Function<WebDriver, T> expectedCondition) {
        if (ignoreTimeoutException) {
            ignoreTimeoutException = false;
            try {
                return until(expectedCondition, interval, timeout, ignoredExceptions);
            } catch (TimeoutException e) {
                return expectedCondition.apply(driver);
            }
        }
        return until(expectedCondition, interval, timeout, ignoredExceptions);
    }

    public <T> T until(
            final Function<WebDriver, T> expectedCondition,
            final Duration interval,
            final Duration timeout) {
        return until(expectedCondition, driver, interval, timeout, ignoredExceptions);
    }

    public <T> T until(
            final Function<WebDriver, T> expectedCondition,
            final Collection<Class<? extends Throwable>> ignoredExceptions) {
        return until(expectedCondition, driver, interval, timeout, ignoredExceptions);
    }

    public <T> T until(
            final Function<WebDriver, T> expectedCondition,
            final Duration interval,
            final Duration timeout,
            final Collection<Class<? extends Throwable>> ignoredExceptions) {
        return until(expectedCondition, driver, interval, timeout, ignoredExceptions);
    }

    public static <T> T until(
            final Function<WebDriver, T> expectedCondition,
            final WebDriver driver,
            final Duration interval,
            final Duration timeout,
            final Collection<Class<? extends Throwable>> ignoredExceptions) {
        return new FluentWait<>(driver)
                .pollingEvery(interval)
                .withTimeout(timeout)
                .ignoreAll(ignoredExceptions)
                .until(expectedCondition);
    }
}
