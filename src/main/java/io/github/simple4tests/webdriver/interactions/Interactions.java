package io.github.simple4tests.webdriver.interactions;

import io.github.simple4tests.webdriver.interactions.enums.Mode;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Interactions extends Core {

    public RActions actions;
    public RBrowser browser;
    public RElement element;
    public RSelect select;

    public Interactions(WebDriver driver) {
        super(driver);
        actions = new RActions(driver);
        browser = new RBrowser(driver);
        element = new RElement(driver);
        select = new RSelect(driver);
    }
    
    /*
        BROWSER
     */

    public Alert getAlert() {
        return browser.getAlert();
    }

    public void switchToDefaultContent() {
        browser.switchToDefaultContent();
    }

    public void switchToParentFrame() {
        browser.switchToParentFrame();
    }

    public void switchToFrame(By by) {
        browser.switchToFrame(by);
    }

    public void switchToFrame(WebElement webElement) {
        browser.switchToFrame(webElement);
    }

    public void switchToFrame(int index) {
        browser.switchToFrame(index);
    }

    public void switchToFrame(String nameOrId) {
        browser.switchToFrame(nameOrId);
    }

    public void switchToTab(int index) {
        browser.switchToTab(index);
    }

    public void closeTab() {
        browser.closeTab();
    }

    /*
        ELEMENT
     */

    public void setMode(Mode mode) {
        element.setMode(mode);
    }

    public void setAutoScroll(boolean autoScroll) {
        element.setAutoScroll(autoScroll);
    }

    public void setImplicitWaits(long totalImplicitWaitInMillis) {
        element.setImplicitWaits(totalImplicitWaitInMillis);
    }

    public void setImplicitWaits(long implicitWaitBeforeChecksInMillis, long implicitWaitAfterChecksInMillis) {
        element.setImplicitWaits(implicitWaitBeforeChecksInMillis, implicitWaitAfterChecksInMillis);
    }

    public void convertAllLocatorsToBy(boolean convertAllLocatorsToBy) {
        element.convertAllLocatorsToBy(convertAllLocatorsToBy);
    }

    public int count(Object locator) {
        return element.at(locator).count();
    }

    public boolean isPresent(Object locator) {
        return element.at(locator).isPresent();
    }

    public boolean isAbsent(Object locator) {
        return element.at(locator).isAbsent();
    }

    public boolean waitToBePresent(Object locator, boolean ignoreTimeoutException) {
        return element.at(locator).waitToBePresent(ignoreTimeoutException);
    }

    public Interactions waitToBePresent(Object locator) {
        element.at(locator).waitToBePresent();
        return this;
    }

    public boolean waitToBeAbsent(Object locator, boolean ignoreTimeoutException) {
        return element.at(locator).waitToBeAbsent(ignoreTimeoutException);
    }

    public Interactions waitToBeAbsent(Object locator) {
        element.at(locator).waitToBeAbsent();
        return this;
    }

    public WebElement getElement(Object locator) {
        return element.at(locator).getElement();
    }

    public Interactions waitToBeInteractable(Object locator) {
        element.at(locator).waitToBeInteractable();
        return this;
    }

    public void setScrollIntoViewOptions(String behavior, String block, String inline) {
        element.setScrollIntoViewOptions(behavior, block, inline);
    }

    public Interactions scrollIntoView(Object locator, String behavior, String block, String inline) {
        element.at(locator).scrollIntoView(behavior, block, inline);
        return this;
    }

    public Interactions scrollIntoView(Object locator) {
        element.at(locator).scrollIntoView();
        return this;
    }

    public WebElement getInteractableElement(Object locator, boolean scrollIntoView) {
        return element.at(locator).getInteractableElement(scrollIntoView);
    }

    public WebElement getInteractableElement(Object locator) {
        return element.at(locator).getInteractableElement();
    }

    public Interactions clickEvent(Object locator) {
        element.at(locator).clickEvent();
        return this;
    }

    public Interactions clickEvent(Object locator, String options) {
        element.at(locator).clickEvent(options);
        return this;
    }

    public Interactions dblclickEvent(Object locator) {
        element.at(locator).dblclickEvent();
        return this;
    }

    public Interactions dblclickEvent(Object locator, String options) {
        element.at(locator).dblclickEvent(options);
        return this;
    }

    public Interactions auxclickEvent(Object locator) {
        element.at(locator).auxclickEvent();
        return this;
    }

    public Interactions auxclickEvent(Object locator, String options) {
        element.at(locator).auxclickEvent(options);
        return this;
    }

    public Interactions contextmenuEvent(Object locator) {
        element.at(locator).contextmenuEvent();
        return this;
    }

    public Interactions contextmenuEvent(Object locator, String options) {
        element.at(locator).contextmenuEvent(options);
        return this;
    }

    public Interactions mouseoverEvent(Object locator) {
        element.at(locator).mouseoverEvent();
        return this;
    }

    public Interactions mouseoverEvent(Object locator, String options) {
        element.at(locator).mouseoverEvent(options);
        return this;
    }

    public Interactions set(Object locator, String attribute, String value) {
        element.at(locator).set(attribute, value);
        return this;
    }

    public Interactions set(Object locator, String attribute, boolean value) {
        element.at(locator).set(attribute, value);
        return this;
    }

    public Object get(Object locator, String attribute) {
        return element.at(locator).get(attribute);
    }

    public Interactions click(Object locator) {
        element.at(locator).click();
        return this;
    }

    public Interactions clearAll(boolean clearAll) {
        element.clearAll(clearAll);
        return this;
    }

    public Interactions clearNext(boolean clearNext) {
        element.clearNext(clearNext);
        return this;
    }

    public Interactions invertClear() {
        element.invertClear();
        return this;
    }

    public Interactions clear(Object locator) {
        element.at(locator).clear();
        return this;
    }

    public Interactions sendKeys(Object locator, CharSequence... value) {
        element.at(locator).sendKeys(value);
        return this;
    }

    public Interactions upload(Object locator, String fileAbsolutePath) {
        element.at(locator).upload(fileAbsolutePath);
        return this;
    }

    public Interactions setSelected(Object locator, boolean select) {
        element.at(locator).setSelected(select);
        return this;
    }

    /*
        SELECT
     */

    public Select getSelect(Object locator) {
        return select.at(locator).select;
    }

    public Interactions selectByVisibleText(Object locator, String visibleText) {
        select.at(locator).selectByVisibleText(visibleText);
        return this;
    }

    public Interactions selectByValue(Object locator, String value) {
        select.at(locator).selectByValue(value);
        return this;
    }

    public Interactions selectByIndex(Object locator, int index) {
        select.at(locator).selectByIndex(index);
        return this;
    }
}
