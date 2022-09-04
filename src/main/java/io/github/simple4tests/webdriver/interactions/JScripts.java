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

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class JScripts {

    public static String getDocumentState(JavascriptExecutor executor) {
        return executor.executeScript("return document.readyState").toString();
    }

    public static void scrollIntoView(JavascriptExecutor executor, WebElement element, String behavior, String block, String inline) {
        String scrollIntoView = String.format(
                "scrollIntoView({behavior: '%s', block: '%s', inline: '%s'})",
                behavior, block, inline);
        executor.executeScript(String.format("arguments[0].%s", scrollIntoView), element);
    }

    public static int countElementsByXpath(JavascriptExecutor executor, String xpath) {
        String countNodes = String.format(
                "document.evaluate(\"%s\", document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null).snapshotLength",
                xpath);
        Object o = null;
        while (null == o) {
            o = executor.executeScript(String.format("return %s;", countNodes));
        }
        return Integer.parseInt(o.toString());
    }

    public static int countElementsBySelector(JavascriptExecutor executor, String selector) {
        String countNodes = String.format(
                "document.querySelectorAll(\"%s\").length",
                selector);
        Object o = null;
        while (null == o) {
            o = executor.executeScript(String.format("return %s;", countNodes));
        }
        return Integer.parseInt(executor.executeScript(String.format("return %s;", countNodes)).toString());
    }

    public static WebElement getElementByXpath(JavascriptExecutor executor, String xpath) {
        String getNode = String.format(
                "document.evaluate(\"%s\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue",
                xpath);
        return (WebElement) executor.executeScript(String.format("return %s;", getNode));
    }

    public static WebElement getElementBySelector(JavascriptExecutor executor, String css) {
        String getNode = String.format(
                "document.querySelector(\"%s\")",
                css);
        return (WebElement) executor.executeScript(String.format("return %s;", getNode));
    }

    public static void set(JavascriptExecutor executor, WebElement element, String attribute, String value) {
        executor.executeScript(String.format("arguments[0].%s='%s';", attribute, value), element);
    }

    public static void set(JavascriptExecutor executor, WebElement element, String attribute, Boolean value) {
        executor.executeScript(String.format("arguments[0].%s='%b';", attribute, value), element);
    }

    public static void click(JavascriptExecutor executor, WebElement element) {
        click(executor, element, "{bubbles: true}");
    }

    public static void click(JavascriptExecutor executor, WebElement element, String options) {
        dispatchEvent(executor, element, "MouseEvent", "click", options);
    }

    public static void dblclick(JavascriptExecutor executor, WebElement element) {
        dblclick(executor, element, "{bubbles: true}");
    }

    public static void dblclick(JavascriptExecutor executor, WebElement element, String options) {
        dispatchEvent(executor, element, "MouseEvent", "dblclick", options);
    }

    public static void auxclick(JavascriptExecutor executor, WebElement element) {
        auxclick(
                executor,
                element,
                "{bubbles: true, clientX: arguments[0].getBoundingClientRect().x, clientY: arguments[0].getBoundingClientRect().y}");
    }

    public static void auxclick(JavascriptExecutor executor, WebElement element, String options) {
        dispatchEvent(executor, element, "MouseEvent", "auxclick", options);
    }

    public static void contextmenu(JavascriptExecutor executor, WebElement element) {
        contextmenu(
                executor,
                element,
                "{bubbles: true, clientX: arguments[0].getBoundingClientRect().x, clientY: arguments[0].getBoundingClientRect().y}");
    }

    public static void contextmenu(JavascriptExecutor executor, WebElement element, String options) {
        dispatchEvent(executor, element, "MouseEvent", "contextmenu", options);
    }

    public static void mouseover(JavascriptExecutor executor, WebElement element) {
        mouseover(executor, element, "{bubbles: true}");
    }

    public static void mouseover(JavascriptExecutor executor, WebElement element, String options) {
        dispatchEvent(executor, element, "MouseEvent", "mouseover", options);
    }

    public static void dispatchEvent(JavascriptExecutor executor, WebElement element, String eventName, String type, String options) {
        if (null == options || options.isEmpty())
            dispatchEvent(executor, element, String.format("new %s('%s')", eventName, type));
        else
            dispatchEvent(executor, element, String.format("new %s('%s', %s)", eventName, type, options));
    }

    public static void dispatchEvent(JavascriptExecutor executor, WebElement element, String event) {
        executor.executeScript(String.format("arguments[0].dispatchEvent(%s);", event), element);
    }
}
