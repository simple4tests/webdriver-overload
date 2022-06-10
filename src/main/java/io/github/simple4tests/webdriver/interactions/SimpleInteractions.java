package io.github.simple4tests.webdriver.interactions;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SimpleInteractions extends Core {

    protected RBrowser browser;
    protected RElement element;
    protected RSelect select;

    public SimpleInteractions(WebDriver driver) {
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

    public SimpleInteractions switchToDefaultContent() {
        browser.switchToDefaultContent();
        return this;
    }

    public SimpleInteractions switchToParentFrame() {
        browser.switchToParentFrame();
        return this;
    }

    public SimpleInteractions switchToFrame(By by) {
        browser.switchToFrame(by);
        return this;
    }

    public SimpleInteractions switchToFrame(WebElement webElement) {
        browser.switchToFrame(webElement);
        return this;
    }

    public SimpleInteractions switchToFrame(int index) {
        browser.switchToFrame(index);
        return this;
    }

    public SimpleInteractions switchToFrame(String nameOrId) {
        browser.switchToFrame(nameOrId);
        return this;
    }

    /*
      Index starts at 0
     */
    public SimpleInteractions switchToTab(int index) {
        browser.switchToTab(index);
        return this;
    }

    public SimpleInteractions closeTab() {
        browser.closeTab();
        return this;
    }

    /*
        ELEMENT
     */

    public SimpleInteractions setScrollIntoViewOptions(String behavior, String block, String inline) {
        element.setScrollIntoViewOptions(behavior, block, inline);
        return this;
    }

    public SimpleInteractions scrollIntoView(By by, String behavior, String block, String inline) {
        element.locatedBy(by).scrollIntoView(behavior, block, inline);
        return this;
    }

    public SimpleInteractions scrollIntoView(By by) {
        element.locatedBy(by).scrollIntoView();
        return this;
    }

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

    public SimpleInteractions waitToBePresent(By by) {
        element.locatedBy(by).waitToBePresent();
        return this;
    }

    public boolean waitToBePresent(By by, boolean ignoreTimeoutException) {
        return element.locatedBy(by).waitToBePresent(ignoreTimeoutException);
    }

    public SimpleInteractions waitToBeAbsent(By by) {
        element.locatedBy(by).waitToBeAbsent();
        return this;
    }

    public boolean waitToBeAbsent(By by, boolean ignoreTimeoutException) {
        return element.locatedBy(by).waitToBeAbsent(ignoreTimeoutException);
    }

    public SimpleInteractions click(By by) {
        element.locatedBy(by).click();
        return this;
    }

    public SimpleInteractions dblclick(By by) {
        element.locatedBy(by).dblclick();
        return this;
    }

    public SimpleInteractions set(By by, String attribute, String value) {
        element.locatedBy(by).set(attribute, value);
        return this;
    }

    public SimpleInteractions set(By by, String attribute, boolean value) {
        element.locatedBy(by).set(attribute, value);
        return this;
    }

    public SimpleInteractions clearAll(boolean clearAll) {
        element.clearAll(clearAll);
        return this;
    }

    public SimpleInteractions clearNext(boolean clearNext) {
        element.clearNext(clearNext);
        return this;
    }

    public SimpleInteractions invertClear() {
        element.invertClear();
        return this;
    }

    public SimpleInteractions clear(By by) {
        element.locatedBy(by).clear();
        return this;
    }

    public SimpleInteractions sendKeys(By by, CharSequence... value) {
        element.locatedBy(by).sendKeys(value);
        return this;
    }

    public SimpleInteractions upload(By by, String fileAbsolutePath) {
        element.locatedBy(by).upload(fileAbsolutePath);
        return this;
    }

    public SimpleInteractions setSelected(By by, boolean select) {
        element.locatedBy(by).setSelected(select);
        return this;
    }

    /*
        SELECT
     */

    public Select getSelect(By by) {
        return select.locatedBy(by).select;
    }

    public SimpleInteractions selectByVisibleText(By by, String visibleText) {
        select.locatedBy(by).selectByVisibleText(visibleText);
        return this;
    }

    public SimpleInteractions selectByValue(By by, String value) {
        select.locatedBy(by).selectByValue(value);
        return this;
    }

    public SimpleInteractions selectByIndex(By by, int index) {
        select.locatedBy(by).selectByIndex(index);
        return this;
    }
}
