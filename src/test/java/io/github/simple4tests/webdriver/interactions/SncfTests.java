package io.github.simple4tests.webdriver.interactions;

import io.github.simple4tests.webdriver.utils._TechActions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SncfTests {

    private final String REFUSE_ALL = "//*[@id='CybotCookiebotDialogBodyLevelButtonLevelOptinDeclineAll']";
    private final String DEPARTURE = "//*[@id='departure-place']";
    private final String DEPARTURE_OPTION_1 = "//*[@id='react-autocomplete-departure-place-1']";
    private final String ARRIVAL = "//*[@id='arrival-place']";
    private final String ARRIVAL_OPTION_1 = "//*[@id='react-autocomplete-arrival-place-1']";
    private final String SEARCH = "//button[@title='Rechercher un itinéraire']";
    private final String RESULTS = "//section//ul/li";

    private WebDriver driver;

    @AfterEach
    public void afterEach() {
        driver.quit();
    }

    @RepeatedTest(2)
    @Tag("Selenium")
    public void selenium_SncfTest(RepetitionInfo info) {
        if (1 == info.getCurrentRepetition()) driver = _TechActions.initChromeDriver();
        else driver = _TechActions.initFirefoxDriver();

        driver.navigate().to("http://www.sncf.com/fr");
        _TechActions.waitElementToBePresent(driver, By.xpath(REFUSE_ALL));
        driver.findElement(By.xpath(REFUSE_ALL)).click();
        driver.findElement(By.xpath(DEPARTURE)).sendKeys("Paris");
        _TechActions.waitElementToBePresent(driver, By.xpath(DEPARTURE_OPTION_1));
        driver.findElement(By.xpath(DEPARTURE_OPTION_1)).click();
        driver.findElement(By.xpath(ARRIVAL)).sendKeys("Lyon");
        _TechActions.waitElementToBePresent(driver, By.xpath(ARRIVAL_OPTION_1));
        driver.findElement(By.xpath(ARRIVAL_OPTION_1)).click();
        driver.findElement(By.xpath(SEARCH)).click();
        _TechActions.waitElementToBePresent(driver, By.xpath(RESULTS));
    }

    @RepeatedTest(4)
    @Tag("Interactions")
    public void interactions_SncfTest(RepetitionInfo info) {
        if (info.getCurrentRepetition() < 3) driver = _TechActions.initChromeDriver();
        else driver = _TechActions.initFirefoxDriver();

        Interactions interactions = new Interactions(driver);
        if (2 == info.getCurrentRepetition() || 4 == info.getCurrentRepetition()) {
            System.out.println("***** convertLocatorTypeToBy = false *****");
            interactions.convertLocatorToBy(false);
        }

        interactions.driver.navigate().to("http://www.sncf.com/fr");
        interactions
                .click(REFUSE_ALL)
                .sendKeys(DEPARTURE, "Paris")
                .click(DEPARTURE_OPTION_1)
                .sendKeys(ARRIVAL, "Lyon")
                .click(ARRIVAL_OPTION_1)
                .click(SEARCH)
                .waitToBePresent(RESULTS);
    }

    @RepeatedTest(4)
    @Tag("InteractionsJS")
    public void interactionsJS_SncfTest(RepetitionInfo info) {
        if (info.getCurrentRepetition() < 3) driver = _TechActions.initChromeDriver();
        else driver = _TechActions.initFirefoxDriver();

        Interactions interactions = new Interactions(driver);
        if (2 == info.getCurrentRepetition() || 4 == info.getCurrentRepetition()) {
            System.out.println("***** convertLocatorTypeToBy = false *****");
            interactions.convertLocatorToBy(false);
        }

        interactions.driver.navigate().to("http://www.sncf.com/fr");
        interactions
                .clickEvent(REFUSE_ALL)
                .sendKeys(DEPARTURE, "Paris")
                .clickEvent(DEPARTURE_OPTION_1)
                .sendKeys(ARRIVAL, "Lyon")
                .clickEvent(ARRIVAL_OPTION_1)
                .clickEvent(SEARCH)
                .waitToBeAbsent(RESULTS).waitToBePresent(RESULTS);
    }
}
