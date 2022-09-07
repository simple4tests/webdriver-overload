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

    protected String scrollBehavior;
    protected String scrollBlock;
    protected String scrollInline;

    protected boolean clearAll;
    protected boolean clearNext;

    protected boolean convertAllLocatorsToBy;
    protected LocatorTypes locatorType;
    protected String xpath;
    protected String selector;
    protected By by;
    protected WebElement webElement;

    public RElement(WebDriver driver) {
        super(driver);
        init();
    }

    protected void init() {
        wait.pollingEvery(Duration.ofMillis(50)).withTimeout(Duration.ofSeconds(15));

        scrollBehavior = DEFAULT_SCROLL_BEHAVIOR;
        scrollBlock = DEFAULT_SCROLL_BLOCK;
        scrollInline = DEFAULT_SCROLL_INLINE;

        clearAll(false);

        resetLocator();
        convertAllLocatorsToBy(true);
    }

    protected LocatorTypes getLocatorType(String locator) {
        if (locator.replace("(", "").startsWith("./")
                || locator.replace("(", "").startsWith("/"))
            return LocatorTypes.XPATH;
        return LocatorTypes.SELECTOR;
    }

    protected RElement resetLocator() {
        this.locatorType = null;
        this.xpath = null;
        this.selector = null;
        this.by = null;
        this.webElement = null;
        return this;
    }

    public RElement convertAllLocatorsToBy(boolean convertAllLocatorsToBy) {
        this.convertAllLocatorsToBy = convertAllLocatorsToBy;
        return this;
    }

    public RElement at(LocatorTypes locatorType, Object locator) {
        switch (locatorType) {
            case XPATH:
                if (locator instanceof String) {
                    if (convertAllLocatorsToBy) {
                        at(LocatorTypes.BY, By.xpath((String) locator));
                    } else {
                        this.locatorType = locatorType;
                        xpath = (String) locator;
                    }
                } else throw new Error("<Locator> type mismatch <LocatorType>");
                break;
            case SELECTOR:
                if (locator instanceof String) {
                    if (convertAllLocatorsToBy) {
                        at(LocatorTypes.BY, By.cssSelector((String) locator));
                    } else {
                        this.locatorType = locatorType;
                        selector = (String) locator;
                    }
                } else throw new Error("<Locator> type mismatch <LocatorType>");
                break;
            case BY:
                if (locator instanceof By) {
                    this.locatorType = locatorType;
                    by = (By) locator;
                } else throw new Error("<Locator> type mismatch <LocatorType>");
                break;
            case WEBELEMENT:
                if (locator instanceof WebElement) {
                    this.locatorType = locatorType;
                    webElement = (WebElement) locator;
                } else throw new Error("<Locator> type mismatch <LocatorType>");
                break;
            default:
                throw new Error("<LocatorType> not defined");
        }
        return this;
    }

    public RElement at(Object locator) {
        if (locator instanceof String) return at(getLocatorType((String) locator), locator);
        if (locator instanceof By) return at(LocatorTypes.BY, locator);
        if (locator instanceof WebElement) return at(LocatorTypes.WEBELEMENT, locator);
        throw new Error("<LocatorType> not supported");
    }

    protected WebElement getWebElement() {
        switch (locatorType) {
            case XPATH:
                return JScripts.getElementByXpath(jsExecutor, xpath);
            case SELECTOR:
                return JScripts.getElementBySelector(jsExecutor, selector);
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
                return JScripts.countElementsByXpath(jsExecutor, xpath);
            case SELECTOR:
                return JScripts.countElementsBySelector(jsExecutor, selector);
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

    public boolean waitToBePresent(boolean ignoreTimeoutException) {
        if (ignoreTimeoutException) wait.ignoreTimeoutException();
        return wait.until(input -> isPresent());
    }

    public RElement waitToBePresent() {
        waitToBePresent(false);
        return this;
    }

    public boolean waitToBeAbsent(boolean ignoreTimeoutException) {
        if (ignoreTimeoutException) wait.ignoreTimeoutException();
        return wait.until(input -> isAbsent());
    }

    public RElement waitToBeAbsent() {
        waitToBeAbsent(false);
        return this;
    }

    public WebElement getElement() {
        waitToBePresent();
        return getWebElement();
    }

    public RElement waitToBeInteractable() {
        waitDocumentToBeComplete();
        if (!isNull(locatorType)) {
            try {
                waitToBePresent();
                wait.until(input -> getWebElement().isDisplayed());
                wait.until(input -> getWebElement().isEnabled());
            } catch (WebDriverException ignored) {
            }
        }
        return this;
    }

    public RElement setScrollIntoViewOptions(String behavior, String block, String inline) {
        this.scrollBehavior = behavior;
        this.scrollBlock = block;
        this.scrollInline = inline;
        return this;
    }

    public RElement scrollIntoView(String behavior, String block, String inline) {
        if (!isNull(locatorType))
            JScripts.scrollIntoView(jsExecutor, waitToBeInteractable().getWebElement(), behavior, block, inline);
        return this;
    }

    public RElement scrollIntoView() {
        return scrollIntoView(scrollBehavior, scrollBlock, scrollInline);
    }

    public WebElement getInteractableElement(boolean scrollIntoView) {
        if (scrollIntoView) scrollIntoView();
        else waitToBeInteractable();
        return getWebElement();
    }

    public WebElement getInteractableElement() {
        return getInteractableElement(true);
    }

    public RElement clickEvent() {
        if (!isNull(locatorType)) JScripts.click(jsExecutor, getInteractableElement());
        return this;
    }

    public RElement clickEvent(String options) {
        if (!isNull(locatorType)) JScripts.click(jsExecutor, getInteractableElement(), options);
        return this;
    }

    public RElement dblclickEvent() {
        if (!isNull(locatorType)) JScripts.dblclick(jsExecutor, getInteractableElement());
        return this;
    }

    public RElement dblclickEvent(String options) {
        if (!isNull(locatorType)) JScripts.dblclick(jsExecutor, getInteractableElement(), options);
        return this;
    }

    public RElement auxclickEvent() {
        if (!isNull(locatorType)) JScripts.auxclick(jsExecutor, getInteractableElement());
        return this;
    }

    public RElement auxclickEvent(String options) {
        if (!isNull(locatorType)) JScripts.auxclick(jsExecutor, getInteractableElement(), options);
        return this;
    }

    public RElement contextmenuEvent() {
        if (!isNull(locatorType)) JScripts.contextmenu(jsExecutor, getInteractableElement());
        return this;
    }

    public RElement contextmenuEvent(String options) {
        if (!isNull(locatorType)) JScripts.contextmenu(jsExecutor, getInteractableElement(), options);
        return this;
    }

    public RElement mouseOverEvent() {
        if (!isNull(locatorType)) JScripts.mouseover(jsExecutor, getInteractableElement());
        return this;
    }

    public RElement mouseOverEvent(String options) {
        if (!isNull(locatorType)) JScripts.mouseover(jsExecutor, getInteractableElement(), options);
        return this;
    }

    public RElement set(String attribute, String value) {
        if (!isNull(locatorType) && !isNull(attribute) && !isNull(value))
            JScripts.set(jsExecutor, getInteractableElement(), attribute, value);
        return this;
    }

    public RElement set(String attribute, boolean value) {
        if (!isNull(locatorType) && !isNull(attribute) && !isNull(value))
            JScripts.set(jsExecutor, getInteractableElement(), attribute, value);
        return this;
    }

    public Object get(String attribute) {
        if (!isNull(locatorType) && !isNull(attribute))
            return JScripts.get(jsExecutor, getInteractableElement(), attribute);
        return null;
    }

    public RElement click() {
//        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("")));
        if (!isNull(locatorType)) getInteractableElement().click();
        return this;
    }

    public RElement clearAll(boolean clearAll) {
        this.clearAll = this.clearNext = clearAll;
        return this;
    }

    public RElement clearNext(boolean clearNext) {
        this.clearNext = clearNext;
        return this;
    }

    public RElement invertClear() {
        this.clearNext = !this.clearAll;
        return this;
    }

    public RElement clear() {
        if (!isNull(locatorType)) {
            WebElement element = getInteractableElement();
            element.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
            element.clear();
        }
        return this;
    }

    public RElement sendKeys(CharSequence... value) {
        if (!isNull(locatorType) && !isNull(value)) {
            if (clearNext || 0 == value.length) {
                clearNext = clearAll;
                clear();
            }
            if (0 < value.length) {
                getInteractableElement().sendKeys(value);
            }
        }
        return this;
    }

    public RElement upload(String fileAbsolutePath) {
        if (!isNull(locatorType) && !isNull(fileAbsolutePath) && !fileAbsolutePath.isEmpty())
            getInteractableElement().sendKeys(fileAbsolutePath);
        return this;
    }

    public RElement setSelected(boolean select) {
        if (!isNull(locatorType) && !isNull(select)) {
            WebElement element = getInteractableElement();
            if (element.isSelected() != select) element.click();
        }
        return this;
    }
}
