package io.github.simple4tests.webdriver.interactions;

import org.openqa.selenium.WebDriver;

public class Interaction extends Core {

    public RBrowser browser;
    public RElement element;
    public RSelect select;

    public Interaction(WebDriver driver) {
        super(driver);
        browser = new RBrowser(driver);
        element = new RElement(driver);
        select = new RSelect(driver);
    }
}
