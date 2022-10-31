package io.github.simple4tests.webdriver.interactions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class GoogleTotoUniversalMusicTests {

    private final String ACCEPT = "//*[@id='L2AGLb']";
    private final String SEARCH_CRITERIA = "//input[@name='q']";
    private final String TOTO_UNIVERSAL_MUSIC = "//h3[normalize-space(.)='Toto - Universal Music France']";
    private final String TOTO_ANNIVERSARY = "//a[contains(.,'Le groupe TOTO fête ses 40 ans sur scène !')]";

    private WebDriver driver;

    @AfterEach
    public void afterEach() {
        driver.quit();
    }

    @RepeatedTest(2)
    @Tag("Selenium")
    public void selenium_GoogleTotoTest(RepetitionInfo info) {
        if (1 == info.getCurrentRepetition()) driver = _TechActions.initChromeDriver();
        else driver = _TechActions.initFirefoxDriver();

        driver.navigate().to("http://www.google.fr");
        driver.findElement(By.xpath(ACCEPT)).click();
        driver.findElement(By.xpath(SEARCH_CRITERIA))
                .sendKeys("toto universal music", Keys.ENTER);
        _TechActions.waitElementToBePresent(
                driver,
                By.xpath(TOTO_UNIVERSAL_MUSIC));
        driver.findElement(By.xpath(TOTO_UNIVERSAL_MUSIC)).click();
        _TechActions.scrollIntoView(driver, By.xpath(TOTO_ANNIVERSARY));
        driver.findElement(By.xpath(TOTO_ANNIVERSARY)).click();
        _TechActions.waitTabToBePresent(driver, 1);
        driver.switchTo().window(driver.getWindowHandles()
                .toArray()[1].toString());
    }

    @RepeatedTest(4)
    @Tag("Interactions")
    public void interactions_GoogleTotoTest(RepetitionInfo info) {
        if (info.getCurrentRepetition() < 3) driver = _TechActions.initChromeDriver();
        else driver = _TechActions.initFirefoxDriver();

        Interactions interactions = new Interactions(driver);
        if (2 == info.getCurrentRepetition() || 4 == info.getCurrentRepetition()) {
            System.out.println("***** convertLocatorTypeToBy = false *****");
            interactions.convertAllLocatorsToBy(false);
        }

        interactions.driver.navigate().to("http://www.google.fr");
        interactions
                .click(ACCEPT)
                .sendKeys(SEARCH_CRITERIA, "toto universal music", Keys.ENTER)
                .click(TOTO_UNIVERSAL_MUSIC)
                .click(TOTO_ANNIVERSARY)
                .switchToTab(1);
    }

    @RepeatedTest(4)
    @Tag("InteractionsJS")
    public void interactionsJS_GoogleTotoTest(RepetitionInfo info) {
        if (info.getCurrentRepetition() < 3) driver = _TechActions.initChromeDriver();
        else driver = _TechActions.initFirefoxDriver();

        Interactions interactions = new Interactions(driver);
        if (2 == info.getCurrentRepetition() || 4 == info.getCurrentRepetition()) {
            System.out.println("***** convertLocatorTypeToBy = false *****");
            interactions.convertAllLocatorsToBy(false);
        }

        interactions.driver.navigate().to("http://www.google.fr");
        interactions
                .clickEvent(ACCEPT)
                .set(SEARCH_CRITERIA, "value", "toto universal music")
                .sendKeys(SEARCH_CRITERIA, Keys.ENTER)
                .clickEvent(TOTO_UNIVERSAL_MUSIC)
                .clickEvent(TOTO_ANNIVERSARY)
                .switchToTab(1);
    }
}
