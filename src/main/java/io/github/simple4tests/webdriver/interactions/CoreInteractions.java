package io.github.simple4tests.webdriver.interactions;

import com.google.common.collect.ImmutableList;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.Collection;

public class CoreInteractions {

    private static final Duration DEFAULT_INTERVAL = Duration.ofMillis(50);
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);
    private static final Collection<Class<? extends Throwable>> DEFAULT_IGNORED_EXCEPTIONS = ImmutableList.of(
            NoSuchElementException.class,
            StaleElementReferenceException.class,
            NoSuchFrameException.class,
            NoAlertPresentException.class);

    private static final String DEFAULT_SCROLL_BEHAVIOR = "auto";
    private static final String DEFAULT_SCROLL_BLOCK = "center";
    private static final String DEFAULT_SCROLL_INLINE = "center";

    protected String scrollBehavior;
    protected String scrollBlock;
    protected String scrollInline;

    protected boolean clear;
    protected boolean clearOnce;

    public WebDriver driver;
    public JavascriptExecutor js;
    public Wait wait;

    public CoreInteractions(WebDriver driver) {
        this.scrollBehavior = DEFAULT_SCROLL_BEHAVIOR;
        this.scrollBlock = DEFAULT_SCROLL_BLOCK;
        this.scrollInline = DEFAULT_SCROLL_INLINE;

        this.clear = false;
        this.clearOnce = false;

        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.wait = new Wait(driver, DEFAULT_INTERVAL, DEFAULT_TIMEOUT, DEFAULT_IGNORED_EXCEPTIONS);
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

    protected boolean isNull(Boolean value) {
        return null == value;
    }

    protected boolean isNull(By by) {
        return null == by;
    }

    public void setScrollIntoViewOptions(String behavior, String block, String inline) {
        this.scrollBehavior = behavior;
        this.scrollBlock = block;
        this.scrollInline = inline;
    }

    public void scrollIntoView(Object element) {
        scrollIntoView(element, scrollBehavior, scrollBlock, scrollInline);
    }

    public void scrollIntoView(Object element, String behavior, String block, String inline) {
        js.executeScript(
                String.format("arguments[0].scrollIntoView({behavior: '%s', block: '%s', inline: '%s'});", behavior, block, inline),
                element);
    }

    public void alwaysClear(boolean alwaysClear) {
        this.clear = alwaysClear;
    }

    public CoreInteractions clear() {
        clearOnce = true;
        return this;
    }

    public Alert getAlert() {
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public void switchToParentFrame() {
        driver.switchTo().parentFrame();
    }

    public void switchToFrame(By by) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
    }

    public void switchToFrame(int index) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
    }

    public void switchToFrame(String nameOrId) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(nameOrId));
    }

    /*
     *  Index starts at 0
     */
    public void switchToTab(int index) {
        wait.until(input -> index < driver.getWindowHandles().size());
        driver.switchTo().window(driver.getWindowHandles().toArray()[index].toString());
    }

    public void closeTab() {
        driver.close();
    }
}
