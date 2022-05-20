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

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;

public class RSelect extends Core {

    private Select select;

    public RSelect(WebDriver driver) {
        super(driver);
        wait.pollingEvery(Duration.ofMillis(50)).withTimeout(Duration.ofSeconds(15));
    }

    public RSelect locatedBy(By by) {
        return locatedBy(new RElement(driver).locatedBy(by));
    }

    public RSelect locatedBy(WebElement element) {
        return locatedBy(new RElement(driver).locatedBy(element));
    }

    public RSelect locatedBy(RElement element) {
        this.select = new Select(element.waitToBeInteractable().scrollIntoView().getWebElement());
        return this;
    }

    public Select getSelect() {
        return select;
    }

    private boolean visibleTextExists(String visibleText) {
        if (isNull(visibleText) || visibleText.isEmpty()) return true;
        for (WebElement option : select.getOptions())
            if (visibleText.trim().equals(option.getText().trim())) {
                return true;
            }
        return false;
    }

    private boolean valueExists(String value) {
        if (isNull(value) || value.isEmpty()) return true;
        for (WebElement option : select.getOptions())
            if (value.equals(option.getAttribute("value"))) {
                return true;
            }
        return false;
    }

    /*
     *  Index starts at 0
     */
    private boolean indexExists(int index) {
        if (index < 0) return true;
        for (WebElement option : select.getOptions())
            if (String.valueOf(index).equals(option.getAttribute("index"))) {
                return true;
            }
        return false;
    }

    private RSelect waitVisibleTextExists(String visibleText) {
        if (!isNull(visibleText)) wait.ignoreTimeoutException().until(input -> visibleTextExists(visibleText));
        return this;
    }

    private RSelect waitValueExists(String value) {
        if (!isNull(value)) wait.ignoreTimeoutException().until(input -> valueExists(value));
        return this;
    }

    /*
      Index starts at 0
     */
    private RSelect waitIndexExists(int index) {
        if (!isNull(index)) wait.ignoreTimeoutException().until(input -> indexExists(index));
        return this;
    }

    public void selectByVisibleText(String visibleText) {
        waitVisibleTextExists(visibleText).getSelect().selectByVisibleText(visibleText);
    }

    public void selectByValue(String value) {
        waitValueExists(value).getSelect().selectByValue(value);
    }

    public void selectByIndex(int index) {
        waitIndexExists(index).getSelect().selectByIndex(index);
    }
}
