package io.github.simple4tests.webdriver.interactions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class RActions extends Core {

    public final Actions actions;
    private final RElement target;

    public RActions(WebDriver driver) {
        super(driver);
        actions = new Actions(driver);
        target = new RElement(driver);
    }

    public void scrollToElement(Object locator) {
        if (!isNull(locator))
            actions.scrollToElement(target.at(locator).getInteractableElement(false)).perform();
    }

    public void keyDown(Object locator, CharSequence key) {
        if (!isNull(locator) && !isNull(key))
            actions.keyDown(target.at(locator).getInteractableElement(false), key).perform();
    }

    public void keyUp(Object locator, CharSequence key) {
        if (!isNull(locator) && !isNull(key))
            actions.keyUp(target.at(locator).getInteractableElement(false), key).perform();
    }

    public void sendKeys(Object locator, CharSequence... keys) {
        if (!isNull(locator) && !isNull(keys))
            actions.sendKeys(target.at(locator).getInteractableElement(false), keys).perform();
    }

    public void clickAndHold(Object locator) {
        if (!isNull(locator))
            actions.clickAndHold(target.at(locator).getInteractableElement(false)).perform();
    }

    public void release(Object locator) {
        if (!isNull(locator))
            actions.release(target.at(locator).getInteractableElement(false)).perform();
    }

    public void click(Object locator) {
        if (!isNull(locator))
            actions.click(target.at(locator).getInteractableElement(false)).perform();
    }

    public void doubleClick(Object locator) {
        if (!isNull(locator))
            actions.doubleClick(target.at(locator).getInteractableElement(false)).perform();
    }

    public void contextClick(Object locator) {
        if (!isNull(locator))
            actions.contextClick(target.at(locator).getInteractableElement(false)).perform();
    }

    public void moveToElement(Object locator) {
        if (!isNull(locator))
            actions.moveToElement(target.at(locator).getInteractableElement(false)).perform();
    }

    public void dragAndDrop(Object sourceLocator, Object targetLocator) {
        if (!isNull(sourceLocator) && !isNull(targetLocator)) {
            actions.dragAndDrop(
                            target.at(sourceLocator).getInteractableElement(false),
                            target.at(targetLocator).getInteractableElement(false))
                    .perform();
        }
    }
}
