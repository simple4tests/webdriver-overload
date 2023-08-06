package io.github.simple4tests.webdriver.interactions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class GoogleReversoTests {

    private final String GOOGLE_ACCEPT = "//*[@id='L2AGLb']";
    private final String GOOGLE_SEARCH_CRITERIA = "//*[@name='q']";
    private final String GOOGLE_PROPOSALS_CRITERIA = "//*[@id='Alh6id']//li";
    private final String REVERSO_ACCEPT = "//button[@id='didomi-notice-agree-button']";
    private final String REVERSO_LINK = "//h3[normalize-space(.)='Traduction gratuite, dictionnaire - Reverso']";
    private final String REVERSO_CONJUGAISON = "//h3[normalize-space(.)='Conjugaison']";

    private WebDriver driver;

    @AfterEach
    public void afterEach() {
        driver.quit();
    }

    @RepeatedTest(2)
    @Tag("Selenium")
    public void selenium_GoogleReversoTest(RepetitionInfo info) {
        if (1 == info.getCurrentRepetition()) driver = _TechActions.initChromeDriver();
        else driver = _TechActions.initFirefoxDriver();

        driver.navigate().to("http://www.google.fr");
        driver.findElement(By.xpath(GOOGLE_ACCEPT)).click();
        driver.findElement(By.xpath(GOOGLE_SEARCH_CRITERIA)).sendKeys("reverso");
        driver.findElement(By.xpath(GOOGLE_SEARCH_CRITERIA)).click();
        _TechActions.waitElementToBePresent(
                driver,
                By.xpath(GOOGLE_PROPOSALS_CRITERIA));
        driver.findElement(By.xpath(GOOGLE_SEARCH_CRITERIA)).sendKeys(Keys.ENTER);
        _TechActions.waitElementToBePresent(
                driver,
                By.xpath(REVERSO_LINK));
        driver.findElement(By.xpath(REVERSO_LINK)).click();
        _TechActions.waitElementToBePresent(
                driver,
                By.xpath(REVERSO_ACCEPT));
        driver.findElement(By.xpath(REVERSO_ACCEPT)).click();
        _TechActions.scrollIntoView(
                driver,
                By.xpath(REVERSO_CONJUGAISON));
        driver.findElement(By.xpath(REVERSO_CONJUGAISON)).click();
        _TechActions.waitTabToBePresent(driver, 1);
        driver.switchTo().window(driver.getWindowHandles()
                .toArray()[1].toString());
    }

    @RepeatedTest(4)
    @Tag("Interactions")
    public void interactions_GoogleReversoTest(RepetitionInfo info) {
        if (info.getCurrentRepetition() < 3) driver = _TechActions.initChromeDriver();
        else driver = _TechActions.initFirefoxDriver();

        Interactions interactions = new Interactions(driver);
        if (2 == info.getCurrentRepetition() || 4 == info.getCurrentRepetition()) {
            System.out.println("***** convertLocatorTypeToBy = false *****");
            interactions.convertAllLocatorsToBy(false);
        }

        interactions.driver.navigate().to("http://www.google.fr");
        interactions
                .click(GOOGLE_ACCEPT)
                .sendKeys(GOOGLE_SEARCH_CRITERIA, "reverso")
                .click(GOOGLE_SEARCH_CRITERIA)
                .waitToBePresent(GOOGLE_PROPOSALS_CRITERIA)
                .sendKeys(GOOGLE_SEARCH_CRITERIA, Keys.ESCAPE, Keys.ENTER)
                .click(REVERSO_LINK)
                .click(REVERSO_ACCEPT)
                .click(REVERSO_CONJUGAISON)
                .switchToTab(1);
    }

    @RepeatedTest(4)
    @Tag("InteractionsJS")
    public void interactionsJS_GoogleReversoTest(RepetitionInfo info) {
        if (info.getCurrentRepetition() < 3) driver = _TechActions.initChromeDriver();
        else driver = _TechActions.initFirefoxDriver();

        Interactions interactions = new Interactions(driver);
        if (2 == info.getCurrentRepetition() || 4 == info.getCurrentRepetition()) {
            System.out.println("***** convertLocatorTypeToBy = false *****");
            interactions.convertAllLocatorsToBy(false);
        }

        interactions.driver.navigate().to("http://www.google.fr");
        interactions
                .clickEvent(GOOGLE_ACCEPT)
                .set(GOOGLE_SEARCH_CRITERIA, "value", "reverso")
                .click(GOOGLE_SEARCH_CRITERIA)
                .waitToBePresent(GOOGLE_PROPOSALS_CRITERIA)
                .sendKeys(GOOGLE_SEARCH_CRITERIA, Keys.ENTER)
                .clickEvent(REVERSO_LINK)
                .clickEvent(REVERSO_ACCEPT)
                .clickEvent(REVERSO_CONJUGAISON)
                .switchToTab(1);
    }
}
