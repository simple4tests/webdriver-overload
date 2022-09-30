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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class RSelect extends Core {

    public Select select;

    public RSelect(WebDriver driver) {
        super(driver);
    }

    public RSelect at(Object locator) {
        return fromRElement(new RElement(driver).at(locator));
    }

    protected RSelect fromRElement(RElement element) {
        this.select = new Select(element.getInteractableElement());
        return this;
    }

    protected boolean visibleTextExists(String visibleText) {
        if (isNull(select) || isNull(visibleText) || visibleText.isEmpty()) return false;
        for (WebElement option : select.getOptions())
            if (visibleText.trim().equals(option.getText().trim())) return true;
        return false;
    }

    protected boolean valueExists(String value) {
        if (isNull(select) || isNull(value) || value.isEmpty()) return false;
        for (WebElement option : select.getOptions())
            if (value.equals(option.getAttribute("value"))) return true;
        return false;
    }

    /*
     *  Index starts at 0
     */
    protected boolean indexExists(int index) {
        if (isNull(select) || index < 0) return false;
        for (WebElement option : select.getOptions())
            if (String.valueOf(index).equals(option.getAttribute("index"))) return true;
        return false;
    }

    protected RSelect waitVisibleTextExists(String visibleText) {
        wait.until(input -> visibleTextExists(visibleText));
        return this;
    }

    protected RSelect waitValueExists(String value) {
        wait.until(input -> valueExists(value));
        return this;
    }

    /*
      Index starts at 0
     */
    protected RSelect waitIndexExists(int index) {
        wait.until(input -> indexExists(index));
        return this;
    }

    public void selectByVisibleText(String visibleText) {
        if (!isNull(select) && !isNull(visibleText))
            waitVisibleTextExists(visibleText).select.selectByVisibleText(visibleText);
    }

    public void selectByValue(String value) {
        if (!isNull(select) && !isNull(value))
            waitValueExists(value).select.selectByValue(value);
    }

    public void selectByIndex(int index) {
        if (!isNull(select) && !isNull(index))
            waitIndexExists(index).select.selectByIndex(index);
    }
}
