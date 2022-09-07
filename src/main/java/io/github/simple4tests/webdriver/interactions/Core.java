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
import org.openqa.selenium.*;

import java.time.Duration;
import java.util.Collection;

public class Core {

    public static final Duration DEFAULT_INTERVAL = Duration.ofMillis(50);
    public static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(15);
    public static final Collection<Class<? extends Throwable>> DEFAULT_IGNORED_EXCEPTIONS = ImmutableList.of(
            NoSuchElementException.class,
            StaleElementReferenceException.class,
            NoSuchFrameException.class,
            NoAlertPresentException.class);

    public WebDriver driver;
    public JavascriptExecutor jsExecutor;
    public Wait wait;

    public Core(WebDriver driver) {
        this.driver = driver;
        this.jsExecutor = (JavascriptExecutor) driver;
        this.wait = new Wait(driver, DEFAULT_INTERVAL, DEFAULT_TIMEOUT, DEFAULT_IGNORED_EXCEPTIONS);
    }

    public Core(Core coreInteraction) {
        this.driver = coreInteraction.driver;
        this.jsExecutor = coreInteraction.jsExecutor;
        this.wait = coreInteraction.wait;
    }

    public Core getCoreInteractions() {
        return this;
    }

    protected boolean isNull(CharSequence... value) {
        if (null == value) {
            return true;
        }
        for (CharSequence cs : value) {
            if (null == cs) {
                return true;
            }
        }
        return false;
    }

    protected boolean isNull(Object o) {
        return null == o;
    }

    public boolean isDocumentComplete() {
        return JScripts.getDocumentState(jsExecutor).equals("complete");
    }

    public Core waitDocumentToBeComplete() {
        waitDocumentToBeComplete(false);
        return this;
    }

    public boolean waitDocumentToBeComplete(boolean ignoreTimeoutException) {
        if (ignoreTimeoutException)
            wait.ignoreTimeoutException();
        return wait.until(input -> isDocumentComplete());
    }
}
