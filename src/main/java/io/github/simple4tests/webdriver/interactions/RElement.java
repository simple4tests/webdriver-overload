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

import org.openqa.selenium.*;

import java.time.Duration;

public class RElement extends Core {

    public static final String DEFAULT_SCROLL_BEHAVIOR = "auto";
    public static final String DEFAULT_SCROLL_BLOCK = "center";
    public static final String DEFAULT_SCROLL_INLINE = "center";

    private String scrollBehavior;
    private String scrollBlock;
    private String scrollInline;

    private LocatorTypes locatorType;
    private String xpath;
    private String selector;
    private By by;
    private WebElement webElement;

    private boolean clearAll;
    private boolean clearNext;

    public RElement(WebDriver driver) {
        super(driver);
        init();
    }

    private void init() {
        wait.pollingEvery(Duration.ofMillis(50)).withTimeout(Duration.ofSeconds(15));

        scrollBehavior = DEFAULT_SCROLL_BEHAVIOR;
        scrollBlock = DEFAULT_SCROLL_BLOCK;
        scrollInline = DEFAULT_SCROLL_INLINE;

        clearNext = clearAll = false;
        resetLocator();
    }

    private void resetLocator() {
        this.locatorType = null;
        this.xpath = null;
        this.selector = null;
        this.by = null;
        this.webElement = null;
    }

    public RElement locatedBy(LocatorTypes locatorType, Object locator) {
        resetLocator();
        switch (locatorType) {
            case XPATH:
                if (locator instanceof String) {
                    this.locatorType = locatorType;
                    this.xpath = (String) locator;
                } else {
                    throw new Error("<Locator> type mismatch <LocatorType>");
                }
                break;
            case SELECTOR:
                if (locator instanceof String) {
                    this.locatorType = locatorType;
                    this.selector = (String) locator;
                } else {
                    throw new Error("<Locator> type mismatch <LocatorType>");
                }
                break;
            case BY:
                if (locator instanceof By) {
                    this.locatorType = locatorType;
                    this.by = (By) locator;
                } else {
                    throw new Error("<Locator> type mismatch <LocatorType>");
                }
                break;
            case WEBELEMENT:
                if (locator instanceof WebElement) {
                    this.locatorType = locatorType;
                    this.webElement = (WebElement) locator;
                } else {
                    throw new Error("<Locator> type mismatch <LocatorType>");
                }
                break;
            default:
                throw new Error("<LocatorType> not defined");
        }
        return this;
    }

    public RElement locatedByXpath(String xpath) {
        return locatedBy(LocatorTypes.XPATH, xpath);
    }

    public RElement locatedBySelector(String selector) {
        return locatedBy(LocatorTypes.SELECTOR, selector);
    }

    public RElement locatedBy(By by) {
        return locatedBy(LocatorTypes.BY, by);
    }

    public RElement locatedBy(WebElement webElement) {
        return locatedBy(LocatorTypes.WEBELEMENT, webElement);
    }

    public WebElement getWebElement() {
        switch (locatorType) {
            case XPATH:
                return JScripts.getElementByXpath(executor, xpath);
            case SELECTOR:
                return JScripts.getElementBySelector(executor, selector);
            case BY:
                return driver.findElement(by);
            case WEBELEMENT:
                return webElement;
            default:
                return null;
        }
    }

    public int count() {
        switch (locatorType) {
            case XPATH:
                return JScripts.countElementsByXpath(executor, xpath);
            case SELECTOR:
                return JScripts.countElementsBySelector(executor, selector);
            case BY:
                return driver.findElements(by).size();
            case WEBELEMENT:
                return webElement.findElements(By.xpath(".")).size();
            default:
                return 0;
        }
    }

    public boolean isPresent() {
        return 0 < count();
    }

    public boolean isAbsent() {
        return 0 == count();
    }

    public void waitToBePresent() {
        waitToBePresent(false);
    }

    public boolean waitToBePresent(boolean ignoreTimeoutException) {
        if (ignoreTimeoutException) wait.ignoreTimeoutException();
        return wait.until(input -> isPresent());
    }

    public void waitToBeAbsent() {
        waitToBeAbsent(false);
    }

    public boolean waitToBeAbsent(boolean ignoreTimeoutException) {
        if (ignoreTimeoutException) wait.ignoreTimeoutException();
        return wait.until(input -> isAbsent());
    }

    public RElement waitToBeInteractable() {
        waitToBePresent();
        waitDocumentToBeComplete();
        try {
            getWebElement().isEnabled();
            wait.until(input -> getWebElement().isEnabled());
        } catch (WebDriverException ignored) {
        }
        return this;
    }

    public void setScrollIntoViewOptions(String behavior, String block, String inline) {
        this.scrollBehavior = behavior;
        this.scrollBlock = block;
        this.scrollInline = inline;
    }

    public void scrollIntoView(String behavior, String block, String inline) {
        JScripts.scrollIntoView(executor, getWebElement(), behavior, block, inline);
    }

    public RElement scrollIntoView() {
        scrollIntoView(scrollBehavior, scrollBlock, scrollInline);
        return this;
    }

    public void click() {
        try {
            waitToBeInteractable().scrollIntoView().getWebElement().click();
        } catch (WebDriverException e) {
            JScripts.click(executor, waitToBeInteractable().scrollIntoView().getWebElement());
        }
    }

    public void dblclick() {
        JScripts.dblclick(executor, waitToBeInteractable().scrollIntoView().getWebElement());
    }

    public void set(String attribute, String value) {
        if (isNull(attribute) || isNull(value)) {
            return;
        }
        JScripts.set(executor, waitToBeInteractable().scrollIntoView().getWebElement(), attribute, value);
    }

    public void set(String attribute, Boolean value) {
        if (isNull(attribute) || isNull(value)) {
            return;
        }
        JScripts.set(executor, waitToBeInteractable().scrollIntoView().getWebElement(), attribute, value);
    }

    public void clearAll(boolean clearAll) {
        this.clearAll = this.clearNext = clearAll;
    }

    public RElement clearNext(boolean clearNext) {
        this.clearNext = clearNext;
        return this;
    }

    public RElement invertClear() {
        this.clearNext = !this.clearAll;
        return this;
    }

    public void clear() {
        WebElement element = waitToBeInteractable().scrollIntoView().getWebElement();
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        element.clear();
    }

    public void sendKeys(CharSequence... value) {
        if (isNull(value)) return;
        if (clearNext || 0 == value.length) {
            clearNext = clearAll;
            clear();
        }
        if (0 < value.length) {
            waitToBeInteractable().scrollIntoView().getWebElement().sendKeys(value);
        }
    }

    public void upload(String fileAbsolutePath) {
        if (isNull(fileAbsolutePath) || fileAbsolutePath.isEmpty()) return;
        waitToBeInteractable().scrollIntoView().getWebElement().sendKeys(fileAbsolutePath);
    }

    public void setSelected(boolean select) {
        if (isNull(select)) {
            return;
        }
        WebElement element = waitToBeInteractable().scrollIntoView().getWebElement();
        if (element.isSelected() != select) {
            element.click();
        }
    }

//    public RSelect getRSelect() {
//        return new RSelect(driver).locatedBy(this);
//    }
}
