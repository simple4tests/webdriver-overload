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

import io.github.simple4tests.webdriver.interactions.enums.LocatorTypes;
import io.github.simple4tests.webdriver.interactions.enums.Mode;
import org.openqa.selenium.*;

public class RElement extends Core {

    private LocatorTypes locatorType;
    private String xpath;
    private String selector;
    private By by;
    private WebElement webElement;

    private boolean clearAll;
    private boolean clearNext;

    public Mode mode;
    public boolean convertAllLocatorsToBy;
    public boolean autoScroll;

    public long implicitWaitBeforeChecksInMillis;
    public long implicitWaitAfterChecksInMillis;

    public RActions actions;
    public RJSActions js;

    public RElement(WebDriver driver) {
        super(driver);
        actions = new RActions(this);
        js = new RJSActions(this);
        init();
    }

    protected void init() {
        resetLocator();
        setMode(Mode.SAFE);
        convertAllLocatorsToBy(true);
        setAutoScroll(true);
        setImplicitWaits(0);
        clearAll(false);
    }

    protected void resetLocator() {
        this.locatorType = null;
        this.xpath = null;
        this.selector = null;
        this.by = null;
        this.webElement = null;
    }

    protected LocatorTypes getLocatorType(Object locator) {
        if (locator instanceof String) {
            if (((String) locator).replace("(", "").startsWith("./")
                    || ((String) locator).replace("(", "").startsWith("/"))
                return LocatorTypes.XPATH;
            return LocatorTypes.SELECTOR;
        }
        if (locator instanceof By) return LocatorTypes.BY;
        if (locator instanceof WebElement) return LocatorTypes.WEBELEMENT;
        return null;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void convertAllLocatorsToBy(boolean convertAllLocatorsToBy) {
        this.convertAllLocatorsToBy = convertAllLocatorsToBy;
    }

    public RElement at(LocatorTypes locatorType, Object locator) {
        resetLocator();
        if (null == locatorType || null == locator) return this;
        switch (locatorType) {
            case XPATH:
                if (locator instanceof String) {
                    if (convertAllLocatorsToBy) return at(LocatorTypes.BY, By.xpath((String) locator));
                    this.locatorType = locatorType;
                    this.xpath = (String) locator;
                } else
                    System.out.println("S4T RElement at(LocatorType, Locator) : <Locator> type mismatch <LocatorType>");
                return this;
            case SELECTOR:
                if (locator instanceof String) {
                    if (convertAllLocatorsToBy) return at(LocatorTypes.BY, By.cssSelector((String) locator));
                    this.locatorType = locatorType;
                    this.selector = (String) locator;
                } else
                    System.out.println("S4T RElement at(LocatorType, Locator) : <Locator> type mismatch <LocatorType>");
                return this;
            case BY:
                if (locator instanceof By) {
                    this.locatorType = locatorType;
                    this.by = (By) locator;
                } else
                    System.out.println("S4T RElement at(LocatorType, Locator) : <Locator> type mismatch <LocatorType>");
                return this;
            case WEBELEMENT:
                if (locator instanceof WebElement) {
                    this.locatorType = locatorType;
                    webElement = (WebElement) locator;
                } else
                    System.out.println("S4T RElement at(LocatorType, Locator) : <Locator> type mismatch <LocatorType>");
                return this;
            default:
                return this;
        }
    }

    public RElement at(Object locator) {
        return at(getLocatorType(locator), locator);
    }

    public void setAutoScroll(boolean autoScroll) {
        this.autoScroll = autoScroll;
    }

    public void setImplicitWaits(long totalImplicitWaitInMillis) {
        setImplicitWaits(
                Long.divideUnsigned(totalImplicitWaitInMillis, 2),
                Long.divideUnsigned(totalImplicitWaitInMillis, 2));
    }

    public void setImplicitWaits(long implicitWaitBeforeChecksInMillis, long implicitWaitAfterChecksInMillis) {
        this.implicitWaitBeforeChecksInMillis = implicitWaitBeforeChecksInMillis;
        this.implicitWaitAfterChecksInMillis = implicitWaitAfterChecksInMillis;
    }

    protected WebElement getWebElement() {
        if (null == locatorType) return null;
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
        if (null == locatorType) return 0;
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
        sleep(implicitWaitBeforeChecksInMillis);
        if (!mode.equals(Mode.INSANE)) {
            if (!isNull(locatorType)) {
                waitToBePresent();
                if (!mode.equals(Mode.LUCKY)) {
                    waitDocumentToBeComplete(true);
                    try {
                        wait.ignoreTimeoutException().until(input -> getElement().isDisplayed());
                        wait.ignoreTimeoutException().until(input -> getElement().isEnabled());
                    } catch (WebDriverException ignored) {
                    }
                }
            }
        }
        sleep(implicitWaitAfterChecksInMillis);
        return this;
    }

    public WebElement getInteractableElement(boolean scrollIntoView) {
//        if (scrollIntoView) scrollIntoView();
//        else waitToBeInteractable();
//        return getWebElement();
        waitToBeInteractable();
        if (scrollIntoView) {
            js.scrollIntoView(getWebElement());
            waitToBeInteractable();
        }
        return getWebElement();
    }

    public WebElement getInteractableElement() {
        return getInteractableElement(autoScroll);
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
        if (!isNull(locatorType) && !isNull(fileAbsolutePath) && !fileAbsolutePath.isEmpty()) {
            Mode currentMode = mode;
            setMode(Mode.LUCKY);
            getInteractableElement().sendKeys(fileAbsolutePath);
            setMode(currentMode);
        }
    }

    public void setSelected(boolean select) {
        if (!isNull(locatorType) && !isNull(select)) {
            WebElement element = getInteractableElement();
            if (element.isSelected() != select) element.click();
        }
    }

    @Deprecated
    public void setScrollIntoViewOptions(String behavior, String block, String inline) {
//        this.scrollBehavior = behavior;
//        this.scrollBlock = block;
//        this.scrollInline = inline;
        js.setScrollIntoViewOptions(behavior, block, inline);
    }

    @Deprecated
    public RElement scrollIntoView(String behavior, String block, String inline) {
//        if (!isNull(locatorType))
//            JScripts.scrollIntoView(jsExecutor, waitToBeInteractable().getWebElement(), behavior, block, inline);
        js.scrollIntoView(getWebElement(), behavior, block, inline);
        return this;
    }

    @Deprecated
    public RElement scrollIntoView() {
//        return scrollIntoView(scrollBehavior, scrollBlock, scrollInline);
        js.scrollIntoView(getWebElement());
        return this;
    }

    @Deprecated
    public void clickEvent() {
        if (!isNull(locatorType))
//            JScripts.click(jsExecutor, getInteractableElement());
            js.clickEvent(getWebElement());
    }

    @Deprecated
    public void clickEvent(String options) {
        if (!isNull(locatorType))
//            JScripts.click(jsExecutor, getInteractableElement(), options);
            js.clickEvent(getWebElement(), options);
    }

    @Deprecated
    public void dblclickEvent() {
        if (!isNull(locatorType))
//            JScripts.dblclick(jsExecutor, getInteractableElement());
            js.dblclickEvent(getWebElement());
    }

    @Deprecated
    public void dblclickEvent(String options) {
        if (!isNull(locatorType))
//            JScripts.dblclick(jsExecutor, getInteractableElement(), options);
            js.dblclickEvent(getWebElement(), options);
    }

    @Deprecated
    public void auxclickEvent() {
        if (!isNull(locatorType))
//            JScripts.auxclick(jsExecutor, getInteractableElement());
            js.auxclickEvent(getWebElement());
    }

    @Deprecated
    public void auxclickEvent(String options) {
        if (!isNull(locatorType))
//            JScripts.auxclick(jsExecutor, getInteractableElement(), options);
            js.auxclickEvent(getWebElement(), options);
    }

    @Deprecated
    public void contextmenuEvent() {
        if (!isNull(locatorType))
//            JScripts.contextmenu(jsExecutor, getInteractableElement());
            js.contextmenuEvent(getWebElement());
    }

    @Deprecated
    public void contextmenuEvent(String options) {
        if (!isNull(locatorType))
//            JScripts.contextmenu(jsExecutor, getInteractableElement(), options);
            js.contextmenuEvent(getWebElement(), options);
    }

    @Deprecated
    public void mouseoverEvent() {
        if (!isNull(locatorType))
//            JScripts.mouseover(jsExecutor, getInteractableElement());
            js.mouseoverEvent(getWebElement());
    }

    @Deprecated
    public void mouseoverEvent(String options) {
        if (!isNull(locatorType))
//            JScripts.mouseover(jsExecutor, getInteractableElement(), options);
            js.mouseoverEvent(getWebElement(), options);
    }

    @Deprecated
    public void set(String attribute, String value) {
        if (!isNull(locatorType) && !isNull(attribute) && !isNull(value))
//            JScripts.set(jsExecutor, getInteractableElement(), attribute, value);
            js.set(getWebElement(), attribute, value);
    }

    @Deprecated
    public void set(String attribute, boolean value) {
        if (!isNull(locatorType) && !isNull(attribute))
//            JScripts.set(jsExecutor, getInteractableElement(), attribute, value);
            js.set(getWebElement(), attribute, value);
    }

    @Deprecated
    public Object get(String attribute) {
        if (!isNull(locatorType) && !isNull(attribute))
//            return JScripts.get(jsExecutor, getInteractableElement(), attribute);
            return js.get(getWebElement(), attribute);
        return null;
    }
}
