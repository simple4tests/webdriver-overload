package io.github.simple4tests.webdriver.interactions;

public class RJSActions {

    public static final String DEFAULT_SCROLL_BEHAVIOR = "auto";
    public static final String DEFAULT_SCROLL_BLOCK = "center";
    public static final String DEFAULT_SCROLL_INLINE = "center";

    public String scrollBehavior;
    public String scrollBlock;
    public String scrollInline;

    private final RElement element;

    public RJSActions(RElement element) {
        this.element = element;
        setScrollIntoViewOptions(DEFAULT_SCROLL_BEHAVIOR, DEFAULT_SCROLL_BLOCK, DEFAULT_SCROLL_INLINE);
    }

    public void setScrollIntoViewOptions(String behavior, String block, String inline) {
        this.scrollBehavior = behavior;
        this.scrollBlock = block;
        this.scrollInline = inline;
    }

    public void scrollIntoView(Object locator, String behavior, String block, String inline) {
        if (null != locator)
            JScripts.scrollIntoView(
                    element.jsExecutor,
//                    element.at(locator).getInteractableElement(false),
                    element.at(locator).getElement(),
                    behavior,
                    block,
                    inline);
    }

    public void scrollIntoView(Object locator) {
        if (null != locator)
            JScripts.scrollIntoView(
                    element.jsExecutor,
//                    element.at(locator).getInteractableElement(false),
                    element.at(locator).getElement(),
                    scrollBehavior,
                    scrollBlock,
                    scrollInline);
    }

    public void clickEvent(Object locator) {
        if (null != locator)
            JScripts.click(
                    element.jsExecutor,
                    element.at(locator).getInteractableElement());
    }

    public void clickEvent(Object locator, String options) {
        if (null != locator)
            JScripts.click(
                    element.jsExecutor,
                    element.at(locator).getInteractableElement(),
                    options);
    }

    public void dblclickEvent(Object locator) {
        if (null != locator)
            JScripts.dblclick(
                    element.jsExecutor,
                    element.at(locator).getInteractableElement());
    }

    public void dblclickEvent(Object locator, String options) {
        if (null != locator)
            JScripts.dblclick(
                    element.jsExecutor,
                    element.at(locator).getInteractableElement(),
                    options);
    }

    public void auxclickEvent(Object locator) {
        if (null != locator)
            JScripts.auxclick(
                    element.jsExecutor,
                    element.at(locator).getInteractableElement());
    }

    public void auxclickEvent(Object locator, String options) {
        if (null != locator)
            JScripts.auxclick(
                    element.jsExecutor,
                    element.at(locator).getInteractableElement(),
                    options);
    }

    public void contextmenuEvent(Object locator) {
        if (null != locator)
            JScripts.contextmenu(
                    element.jsExecutor,
                    element.at(locator).getInteractableElement());
    }

    public void contextmenuEvent(Object locator, String options) {
        if (null != locator)
            JScripts.contextmenu(
                    element.jsExecutor,
                    element.at(locator).getInteractableElement(),
                    options);
    }

    public void mouseoverEvent(Object locator) {
        if (null != locator)
            JScripts.mouseover(
                    element.jsExecutor,
                    element.at(locator).getInteractableElement());
    }

    public void mouseoverEvent(Object locator, String options) {
        if (null != locator)
            JScripts.mouseover(
                    element.jsExecutor,
                    element.at(locator).getInteractableElement(),
                    options);
    }

    public void set(Object locator, String attribute, String value) {
        if (null != locator && null != attribute && null != value)
            JScripts.set(
                    element.jsExecutor,
                    element.at(locator).getInteractableElement(),
                    attribute,
                    value);
    }

    public void set(Object locator, String attribute, boolean value) {
        if (null != locator && null != attribute)
            JScripts.set(
                    element.jsExecutor,
                    element.at(locator).getInteractableElement(),
                    attribute,
                    value);
    }

    public Object get(Object locator, String attribute) {
        if (null != locator && null != attribute)
            return JScripts.get(
                    element.jsExecutor,
                    element.at(locator).getElement(),
                    attribute);
        return null;
    }
}
