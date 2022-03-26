package io.github.simple4tests.webdriver.interactions;

import com.google.common.collect.ImmutableList;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.Collection;

public class WebElementInteractions extends CoreInteractions {

    public static final Duration DEFAULT_INTERVAL = Duration.ofMillis(50);
    public static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);
    public static final Collection<Class<? extends Throwable>> DEFAULT_IGNORED_EXCEPTIONS = ImmutableList.of(
            NoSuchElementException.class,
            StaleElementReferenceException.class,
            NoSuchFrameException.class,
            NoAlertPresentException.class);

    public final Wait wait;

    public WebElementInteractions(WebDriver driver) {
        super(driver);
        this.wait = new Wait(driver, DEFAULT_INTERVAL, DEFAULT_TIMEOUT, DEFAULT_IGNORED_EXCEPTIONS);
    }

    public int countElements(By by) {
        return isNull(by) ? 0 : driver.findElements(by).size();
    }

    public boolean isElementPresent(By by) {
        return 0 < countElements(by);
    }

    public boolean isElementAbsent(By by) {
        return 0 == countElements(by);
    }

    public void waitElementToBePresent(By by) {
        wait.until(input -> isElementPresent(by));
    }

    public void waitElementToBeAbsent(By by) {
        wait.until(input -> isElementAbsent(by));
    }

    public WebElement getElement(By by) {
        waitElementToBePresent(by);
        return driver.findElement(by);
    }

    public WebElement getInteractableElement(By by) {
        return getInteractableElement(by, false, true, true);
    }

    public WebElement getInteractableElement(By by, boolean waitUntilElementIsDisplayed, boolean waitUntilElementIsEnabled, boolean scrollIntoView) {
        WebElement element = getElement(by);
        if (waitUntilElementIsDisplayed) wait.until(input -> element.isDisplayed());
        if (waitUntilElementIsEnabled) wait.until(input -> element.isEnabled());
        if (scrollIntoView)
            scrollIntoView(element);
        return element;
    }

    public void click(By by) {
        if (isNull(by)) {
            return;
        }
        try {
            getInteractableElement(by).click();
        } catch (ElementNotInteractableException e) {
            js.executeScript(
                    "arguments[0].dispatchEvent(new MouseEvent('click', {bubbles: true, cancelable: true, view: window}));",
                    driver.findElement(by));
        }
    }

    public void doubleClick(By by) {
        if (isNull(by)) {
            return;
        }
        js.executeScript(
                "arguments[0].dispatchEvent(new MouseEvent('dblclick', {bubbles: true, cancelable: true, view: window}));",
                getInteractableElement(by));
    }

    public void clear(By by) {
        if (isNull(by)) {
            return;
        }
        WebElement element = getInteractableElement(by);
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        element.clear();
    }

    public void set(By by, CharSequence... value) {
        if (isNull(by) || isNull(value)) {
            return;
        }
        if (clear || clearOnce || 0 == value.length) {
            clearOnce = false;
            clear(by);
        }
        if (0 < value.length) {
            getInteractableElement(by).sendKeys(value);
        }
    }

    public void upload(By by, String fileAbsolutePath) {
        if (isNull(by) || isNull(fileAbsolutePath) || fileAbsolutePath.isEmpty()) {
            return;
        }
        getInteractableElement(by).sendKeys(fileAbsolutePath);
    }

    public void checked(By by, Boolean value) {
        if (isNull(by) || isNull(value)) {
            return;
        }
        WebElement element = getInteractableElement(by);
        if (!value.equals(element.isSelected())) {
            element.click();
        }
    }

    public Select getSelectWithVisibleText(By by, String visibleText) {
        Select select = getSelect(by);
        if (null != select) wait.ignoreTimeoutException().until(input -> visibleTextExists(select, visibleText));
        return select;
    }

    public Select getSelectWithValue(By by, String value) {
        Select select = getSelect(by);
        if (null != select) wait.ignoreTimeoutException().until(input -> valueExists(select, value));
        return select;
    }

    /*
      Index starts at 0
     */
    public Select getSelectWithIndex(By by, int index) {
        Select select = getSelect(by);
        if (null != select) wait.ignoreTimeoutException().until(input -> indexExists(select, index));
        return select;
    }

    public Select getSelect(By by) {
        if (isNull(by)) {
            return null;
        }
        return new Select(getInteractableElement(by));
    }

    public boolean visibleTextExists(Select select, String visibleText) {
        if (isNull(visibleText) || visibleText.isEmpty()) return true;
        for (WebElement option : select.getOptions())
            if (visibleText.trim().equals(option.getText().trim())) {
                return true;
            }
        return false;
    }

    public boolean valueExists(Select select, String value) {
        if (isNull(value) || value.isEmpty()) return true;
        for (WebElement option : select.getOptions())
            if (value.equals(option.getAttribute("value"))) {
                return true;
            }
        return false;
    }

    /*
     *  Index starts at 0
     */
    public boolean indexExists(Select select, int index) {
        if (index < 0) return true;
        for (WebElement option : select.getOptions())
            if (String.valueOf(index).equals(option.getAttribute("index"))) {
                return true;
            }
        return false;
    }
}
