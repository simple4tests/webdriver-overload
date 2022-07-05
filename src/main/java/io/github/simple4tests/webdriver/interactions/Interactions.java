package io.github.simple4tests.webdriver.interactions;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

public class Interactions extends Core {

    public RBrowser browser;
    public RElement element;
    public RSelect select;

    public Interactions(WebDriver driver) {
        super(driver);
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

    public Interactions switchToDefaultContent() {
        browser.switchToDefaultContent();
        return this;
    }

    public Interactions switchToParentFrame() {
        browser.switchToParentFrame();
        return this;
    }

    public Interactions switchToFrame(By by) {
        browser.switchToFrame(by);
        return this;
    }

    public Interactions switchToFrame(WebElement webElement) {
        browser.switchToFrame(webElement);
        return this;
    }

    public Interactions switchToFrame(int index) {
        browser.switchToFrame(index);
        return this;
    }

    public Interactions switchToFrame(String nameOrId) {
        browser.switchToFrame(nameOrId);
        return this;
    }

    /*
      Index starts at 0
     */
    public Interactions switchToTab(int index) {
        browser.switchToTab(index);
        return this;
    }

    public Interactions closeTab() {
        browser.closeTab();
        return this;
    }

    /*
        ELEMENT
     */

    public WebElement getElement(By by) {
        return element.locatedBy(by).getElement();
    }

    public WebElement getInteractableElement(By by) {
        return element.locatedBy(by).getInteractableElement();
    }

    public WebElement getInteractableElement(By by, boolean scrollIntoView) {
        return element.locatedBy(by).getInteractableElement(scrollIntoView);
    }

    public int count(By by) {
        return element.locatedBy(by).count();
    }

    public boolean isPresent(By by) {
        return element.locatedBy(by).isPresent();
    }

    public boolean isAbsent(By by) {
        return element.locatedBy(by).isAbsent();
    }

    public Interactions waitToBePresent(By by) {
        element.locatedBy(by).waitToBePresent();
        return this;
    }

    public boolean waitToBePresent(By by, boolean ignoreTimeoutException) {
        return element.locatedBy(by).waitToBePresent(ignoreTimeoutException);
    }

    public Interactions waitToBeAbsent(By by) {
        element.locatedBy(by).waitToBeAbsent();
        return this;
    }

    public boolean waitToBeAbsent(By by, boolean ignoreTimeoutException) {
        return element.locatedBy(by).waitToBeAbsent(ignoreTimeoutException);
    }

    public Interactions waitToBeInteractable(By by) {
        element.locatedBy(by).waitToBeInteractable();
        return this;
    }

    public Interactions setScrollIntoViewOptions(String behavior, String block, String inline) {
        element.setScrollIntoViewOptions(behavior, block, inline);
        return this;
    }

    public Interactions scrollIntoView(By by, String behavior, String block, String inline) {
        element.locatedBy(by).scrollIntoView(behavior, block, inline);
        return this;
    }

    public Interactions scrollIntoView(By by) {
        element.locatedBy(by).scrollIntoView();
        return this;
    }

    public Interactions click(By by) {
        element.locatedBy(by).click();
        return this;
    }

    public Interactions dblClick(By by) {
        element.locatedBy(by).dblClick();
        return this;
    }

    public Interactions rightClick(By by) {
        element.locatedBy(by).rightClick();
        return this;
    }

    public Interactions mouseOver(By by) {
        element.locatedBy(by).mouseOver();
        return this;
    }

    public Interactions set(By by, String attribute, String value) {
        element.locatedBy(by).set(attribute, value);
        return this;
    }

    public Interactions set(By by, String attribute, boolean value) {
        element.locatedBy(by).set(attribute, value);
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

    public Interactions clear(By by) {
        element.locatedBy(by).clear();
        return this;
    }

    public Interactions sendKeys(By by, CharSequence... value) {
        element.locatedBy(by).sendKeys(value);
        return this;
    }

    public Interactions upload(By by, String fileAbsolutePath) {
        element.locatedBy(by).upload(fileAbsolutePath);
        return this;
    }

    public Interactions setSelected(By by, boolean select) {
        element.locatedBy(by).setSelected(select);
        return this;
    }

    /*
        SELECT
     */

    public Select getSelect(By by) {
        return select.locatedBy(by).select;
    }

    public Interactions selectByVisibleText(By by, String visibleText) {
        select.locatedBy(by).selectByVisibleText(visibleText);
        return this;
    }

    public Interactions selectByValue(By by, String value) {
        select.locatedBy(by).selectByValue(value);
        return this;
    }

    public Interactions selectByIndex(By by, int index) {
        select.locatedBy(by).selectByIndex(index);
        return this;
    }
}
