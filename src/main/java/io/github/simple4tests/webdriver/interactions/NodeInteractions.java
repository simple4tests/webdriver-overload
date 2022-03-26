package io.github.simple4tests.webdriver.interactions;

import com.google.common.collect.ImmutableList;
import org.openqa.selenium.WebDriver;

import java.util.Collection;

public class NodeInteractions extends WebElementInteractions {

    protected static final Collection<Class<? extends Throwable>> JS_IGNORED_EXCEPTIONS = ImmutableList.of();

    public NodeInteractions(WebDriver driver) {
        super(driver);
    }

    public int countNodes(String xpath) {
        if (isNull(xpath)) return 0;
        String script = String.format(
                "document.evaluate(\"%s\",document,null,XPathResult.ORDERED_NODE_SNAPSHOT_TYPE,null).snapshotLength;",
                xpath);
        return Integer.parseInt(js.executeScript(script).toString());
    }

    public boolean isNodePresent(String xpath) {
        return 0 < countNodes(xpath);
    }

    public boolean isNodeAbsent(String xpath) {
        return 0 == countNodes(xpath);
    }

    public boolean isDocumentComplete() {
        return js.executeScript("document.readyState").toString().equals("complete");
    }

    public void waitNodeToBePresent(String xpath) {
        wait.until(input -> isNodePresent(xpath), JS_IGNORED_EXCEPTIONS);
    }

    public void waitNodeToBeAbsent(String xpath) {
        wait.until(input -> isNodeAbsent(xpath), JS_IGNORED_EXCEPTIONS);
    }

    public void waitDocumentToBeComplete() {
        wait.until(input -> isDocumentComplete(), JS_IGNORED_EXCEPTIONS);
    }

    public Object getNode(String xpath) {
        waitNodeToBePresent(xpath);
        String script = String.format(
                "document.evaluate(\"%s\",document,null,XPathResult.FIRST_ORDERED_NODE_TYPE,null).singleNodeValue;",
                xpath);
        return js.executeScript(script);
    }

    public Object getInteractableNode(String xpath) {
        return getInteractableNode(xpath, true, true);
    }

    public Object getInteractableNode(String xpath, boolean waitDocumentToBeComplete, boolean scrollIntoView) {
        Object element = getNode(xpath);
        if (waitDocumentToBeComplete) waitDocumentToBeComplete();
        if (scrollIntoView)
            scrollIntoView(element);
        return element;
    }

    public void click(String xpath) {
        if (isNull(xpath)) {
            return;
        }
        js.executeScript(
                "arguments[0].dispatchEvent(new MouseEvent('click', {bubbles: true, cancelable: true, view: window}));",
                getInteractableNode(xpath));
    }

    public void doubleClick(String xpath) {
        if (isNull(xpath)) {
            return;
        }
        js.executeScript(
                "arguments[0].dispatchEvent(new MouseEvent('dblclick', {bubbles: true, cancelable: true, view: window}));",
                getInteractableNode(xpath));
    }

    public void clear(String xpath) {
        if (isNull(xpath)) {
            return;
        }
        js.executeScript(
                "arguments[0].value='';",
                getInteractableNode(xpath));
    }

    public void set(String xpath, String value) {
        if (isNull(xpath) || isNull(value)) {
            return;
        }
        if (clear || clearOnce || value.isEmpty()) {
            clearOnce = false;
            clear(xpath);
        }
        if (!value.isEmpty()) {
            js.executeScript(
                    String.format("arguments[0].value='%s';", value),
                    getInteractableNode(xpath));
        }
    }

//    public void upload(String xpath, String fileAbsolutePath) {
//        if (isNull(xpath) || isNull(fileAbsolutePath) || fileAbsolutePath.isEmpty()) {
//            return;
//        }
//        js.executeScript(
//                String.format("arguments[0].value='%s';", fileAbsolutePath),
//                getInteractableNode(xpath));
//    }

    public void checked(String xpath, Boolean value) {
        if (isNull(xpath) || isNull(value)) {
            return;
        }
        js.executeScript(
                String.format("arguments[0].checked=%b;", value),
                getInteractableNode(xpath));
    }
}
