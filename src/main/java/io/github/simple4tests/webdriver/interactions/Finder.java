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

import org.openqa.selenium.WebDriver;

public class Finder extends Core {

    String templates = "//*[contains(text(), \"LABEL\")]";

    RElement element;

    public Finder(WebDriver driver) {
        super(driver);
        element = new RElement(driver);
    }

    public boolean elementIsPresent(String label) {
        return element.locatedByXpath(templates.replace("LABEL", label)).isAbsent();
    }

//    public RElement element(LocatorTypes locatorType, String locator) {
//        return element.locatedBy(locatorType, locator);
//    }
//
//    public RElement element(By by) {
//        return element.locatedBy(by);
//    }
//
//    public RElement element(WebElement webElement) {
//        return element.locatedBy(webElement);
//    }
//
//    public int count(LocatorTypes locatorType, String locator) {
//        return element(locatorType, locator).count();
//    }
//
//    public int count(By by) {
//        return element(by).count();
//    }
//
//    public int count(WebElement webElement) {
//        return element(webElement).count();
//    }
//
//    public boolean isPresent(LocatorTypes locatorType, String locator) {
//        return element(locatorType, locator).isPresent();
//    }
//
//    public boolean isPresent(By by) {
//        return element(by).isPresent();
//    }
//
//    public boolean isPresent(WebElement webElement) {
//        return element(webElement).isPresent();
//    }
//
//    public boolean isAbsent(LocatorTypes locatorType, String locator) {
//        return element(locatorType, locator).isAbsent();
//    }
//
//    public boolean isAbsent(By by) {
//        return element(by).isAbsent();
//    }
//
//    public boolean isAbsent(WebElement webElement) {
//        return element(webElement).isAbsent();
//    }
//
//    public RElement waitToBePresent(LocatorTypes locatorType, String locator) {
//        return element(locatorType, locator).waitToBePresent();
//    }
//
//    public boolean waitToBePresent(LocatorTypes locatorType, String locator, boolean ignoreTimeout) {
//        return element(locatorType, locator).waitToBePresent(ignoreTimeout);
//    }
//
//    public boolean waitToBePresent(LocatorTypes locatorType, String locator) {
//        return element(locatorType, locator).waitToBePresent(true);
//    }
//
//    public boolean waitToBePresent(By by) {
//        return element(by).waitToBePresent(true);
//    }
//
//    public boolean waitToBePresent(WebElement webElement) {
//        return element(webElement).waitToBePresent(true);
//    }
//
//    public boolean waitToBeAbsent(LocatorTypes locatorType, String locator) {
//        return element(locatorType, locator).waitToBeAbsent(true);
//    }
//
//    public boolean waitToBeAbsent(By by) {
//        return element(by).waitToBeAbsent(true);
//    }
//
//    public boolean waitToBeAbsent(WebElement webElement) {
//        return element(webElement).waitToBeAbsent(true);
//    }
//
//    public RSelect rSelect(LocatorTypes locatorType, String locator) {
//        return new RSelect(driver).from(element(locatorType, locator));
//    }
//
//    public RSelect rSelect(By by) {
//        return new RSelect(driver).from(element(by));
//    }
//
//    public RSelect rSelect(WebElement element) {
//        return new RSelect(driver).from(element(element));
//    }
}
