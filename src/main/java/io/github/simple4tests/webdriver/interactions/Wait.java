/*
MIT License

Copyright (c) 2022 simple4tests

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

package io.github.simple4tests.webdriver.interactions;

import com.google.common.collect.ImmutableList;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.Collection;
import java.util.function.Function;

public class Wait {

    public static final Duration DEFAULT_INTERVAL = Duration.ofMillis(250);
    public static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(30);
    public static final Collection<Class<? extends Throwable>> DEFAULT_IGNORED_EXCEPTIONS = ImmutableList.of();

    private final WebDriver driver;
    private Duration interval;
    private Duration timeout;
    private Collection<Class<? extends Throwable>> ignoredExceptions;
    private boolean ignoreTimeoutException;

    public Wait(WebDriver driver) {
        this(driver, DEFAULT_INTERVAL, DEFAULT_TIMEOUT, DEFAULT_IGNORED_EXCEPTIONS);
    }

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
        return until(expectedCondition, interval, timeout, ignoredExceptions);
    }

    public <T> T until(
            final Function<WebDriver, T> expectedCondition,
            final Duration interval,
            final Duration timeout) {
        return until(expectedCondition, interval, timeout, ignoredExceptions);
    }

    public <T> T until(
            final Function<WebDriver, T> expectedCondition,
            final Collection<Class<? extends Throwable>> ignoredExceptions) {
        return until(expectedCondition, interval, timeout, ignoredExceptions);
    }

    public <T> T until(
            final Function<WebDriver, T> expectedCondition,
            final Duration interval,
            final Duration timeout,
            final Collection<Class<? extends Throwable>> ignoredExceptions) {
        if (ignoreTimeoutException) {
            ignoreTimeoutException = false;
            try {
                return until(expectedCondition, driver, interval, timeout, ignoredExceptions);
            } catch (TimeoutException e) {
                return expectedCondition.apply(driver);
            }
        }
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
