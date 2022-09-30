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

public class RElement extends Core {

    public static final String DEFAULT_SCROLL_BEHAVIOR = "auto";
    public static final String DEFAULT_SCROLL_BLOCK = "center";
    public static final String DEFAULT_SCROLL_INLINE = "center";

    private LocatorTypes locatorType;
    private String xpath;
    private String selector;
    private By by;
    private WebElement webElement;

    private boolean clearAll;
    private boolean clearNext;

    public boolean convertAllLocatorsToBy;
    public boolean autoScroll;

    public long waitBeforeInMillis;
    public long waitAfterInMillis;

    public String scrollBehavior;
    public String scrollBlock;
    public String scrollInline;

    public RElement(WebDriver driver) {
        super(driver);
        init();
    }

    protected void init() {
        resetLocator();
        convertAllLocatorsToBy(true);
        setAutoScroll(true);
        setImplicitWaits(0);
        setScrollIntoViewOptions(DEFAULT_SCROLL_BEHAVIOR, DEFAULT_SCROLL_BLOCK, DEFAULT_SCROLL_INLINE);
        clearAll(false);
    }

    protected LocatorTypes getLocatorType(String locator) {
        if (locator.replace("(", "").startsWith("./")
                || locator.replace("(", "").startsWith("/"))
            return LocatorTypes.XPATH;
        return LocatorTypes.SELECTOR;
    }

    protected void resetLocator() {
        this.locatorType = null;
        this.xpath = null;
        this.selector = null;
        this.by = null;
        this.webElement = null;
    }

    public void convertAllLocatorsToBy(boolean convertAllLocatorsToBy) {
        this.convertAllLocatorsToBy = convertAllLocatorsToBy;
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

    public void setAutoScroll(boolean autoScroll) {
        this.autoScroll = autoScroll;
    }

    public void setImplicitWaits(long waitInBetweenInMillis) {
        setImplicitWaits(
                Long.divideUnsigned(waitInBetweenInMillis, 2),
                Long.divideUnsigned(waitInBetweenInMillis, 2));
    }

    public void setImplicitWaits(long waitBeforeInMillis, long waitAfterInMillis) {
        this.waitBeforeInMillis = waitBeforeInMillis;
        this.waitAfterInMillis = waitAfterInMillis;
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
        return waitToBePresent().getWebElement();
    }

    public RElement waitToBeInteractable() {
        sleep(waitAfterInMillis);
        waitDocumentToBeComplete();
        if (!isNull(locatorType)) {
            WebElement element = getElement();
            try {
                wait.until(input -> element.isDisplayed());
            } catch (WebDriverException ignored) {
            }
            try {
                wait.until(input -> element.isEnabled());
            } catch (WebDriverException ignored) {
            }
        }
        sleep(waitBeforeInMillis);
        return this;
    }

    public void setScrollIntoViewOptions(String behavior, String block, String inline) {
        this.scrollBehavior = behavior;
        this.scrollBlock = block;
        this.scrollInline = inline;
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
        return getInteractableElement(autoScroll);
    }

    public void clickEvent() {
        if (!isNull(locatorType))
            JScripts.click(jsExecutor, getInteractableElement());
    }

    public void clickEvent(String options) {
        if (!isNull(locatorType))
            JScripts.click(jsExecutor, getInteractableElement(), options);
    }

    public void dblclickEvent() {
        if (!isNull(locatorType))
            JScripts.dblclick(jsExecutor, getInteractableElement());
    }

    public void dblclickEvent(String options) {
        if (!isNull(locatorType))
            JScripts.dblclick(jsExecutor, getInteractableElement(), options);
    }

    public void auxclickEvent() {
        if (!isNull(locatorType))
            JScripts.auxclick(jsExecutor, getInteractableElement());
    }

    public void auxclickEvent(String options) {
        if (!isNull(locatorType))
            JScripts.auxclick(jsExecutor, getInteractableElement(), options);
    }

    public void contextmenuEvent() {
        if (!isNull(locatorType))
            JScripts.contextmenu(jsExecutor, getInteractableElement());
    }

    public void contextmenuEvent(String options) {
        if (!isNull(locatorType))
            JScripts.contextmenu(jsExecutor, getInteractableElement(), options);
    }

    public void mouseoverEvent() {
        if (!isNull(locatorType))
            JScripts.mouseover(jsExecutor, getInteractableElement());
    }

    public void mouseoverEvent(String options) {
        if (!isNull(locatorType))
            JScripts.mouseover(jsExecutor, getInteractableElement(), options);
    }

    public void set(String attribute, String value) {
        if (!isNull(locatorType) && !isNull(attribute) && !isNull(value))
            JScripts.set(jsExecutor, getInteractableElement(), attribute, value);
    }

    public void set(String attribute, boolean value) {
        if (!isNull(locatorType) && !isNull(attribute) && !isNull(value))
            JScripts.set(jsExecutor, getInteractableElement(), attribute, value);
    }

    public Object get(String attribute) {
        if (!isNull(locatorType) && !isNull(attribute))
            return JScripts.get(jsExecutor, getInteractableElement(), attribute);
        return null;
    }

    public void click() {
        if (!isNull(locatorType))
            getInteractableElement().click();
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

    public void clear() {
        if (!isNull(locatorType))
            getInteractableElement().sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        clearNext(clearAll);
    }

    public void sendKeys(CharSequence... value) {
        if (!isNull(locatorType) && !isNull(value)) {
            if (clearNext || 0 == value.length) clear();
            if (0 < value.length) getInteractableElement().sendKeys(value);
        }
        clearNext(clearAll);
    }

    public void upload(String fileAbsolutePath) {
        clearNext(false).sendKeys(fileAbsolutePath);
    }

    public void setSelected(boolean select) {
        if (!isNull(locatorType) && !isNull(select)) {
            WebElement element = getInteractableElement();
            if (element.isSelected() != select) element.click();
        }
    }
}
