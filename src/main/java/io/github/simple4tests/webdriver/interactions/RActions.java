package io.github.simple4tests.webdriver.interactions;

import org.openqa.selenium.interactions.Actions;

public class RActions {

    public final Actions actions;
    private final RElement element;

    public RActions(RElement element) {
        this.actions = new Actions(element.driver);
        this.element = element;
    }

    public void scrollToElement(Object locator) {
        if (!element.isNull(locator))
//            actions.scrollToElement(element.at(locator).getInteractableElement(false)).perform();
            actions.scrollToElement(element.at(locator).getElement()).perform();
    }

    public void keyDown(Object locator, CharSequence key) {
        if (!element.isNull(locator) && !element.isNull(key))
            actions.keyDown(element.at(locator).getInteractableElement(false), key).perform();
    }

    public void keyUp(Object locator, CharSequence key) {
        if (!element.isNull(locator) && !element.isNull(key))
            actions.keyUp(element.at(locator).getInteractableElement(false), key).perform();
    }

    public void sendKeys(Object locator, CharSequence... keys) {
        if (!element.isNull(locator) && !element.isNull(keys))
            actions.sendKeys(element.at(locator).getInteractableElement(false), keys).perform();
    }

    public void clickAndHold(Object locator) {
        if (!element.isNull(locator))
            actions.clickAndHold(element.at(locator).getInteractableElement(false)).perform();
    }

    public void release(Object locator) {
        if (!element.isNull(locator))
            actions.release(element.at(locator).getInteractableElement(false)).perform();
    }

    public void click(Object locator) {
        if (!element.isNull(locator))
            actions.click(element.at(locator).getInteractableElement(false)).perform();
    }

    public void doubleClick(Object locator) {
        if (!element.isNull(locator))
            actions.doubleClick(element.at(locator).getInteractableElement(false)).perform();
    }

    public void contextClick(Object locator) {
        if (!element.isNull(locator))
            actions.contextClick(element.at(locator).getInteractableElement(false)).perform();
    }

    public void moveToElement(Object locator) {
        if (!element.isNull(locator))
            actions.moveToElement(element.at(locator).getInteractableElement(false)).perform();
    }

    public void dragAndDrop(Object sourceLocator, Object targetLocator) {
        if (!element.isNull(sourceLocator) && !element.isNull(targetLocator)) {
            actions.dragAndDrop(
                            element.at(sourceLocator).getInteractableElement(false),
                            element.at(targetLocator).getInteractableElement(false))
                    .perform();
        }
    }
}
