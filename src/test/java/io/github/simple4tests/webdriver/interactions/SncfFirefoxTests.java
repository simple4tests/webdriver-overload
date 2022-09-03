package io.github.simple4tests.webdriver.interactions;

import io.github.simple4tests.webdriver.utils._TechActions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public class SncfFirefoxTests {

    private final String REFUSE_ALL = "//*[@id='CybotCookiebotDialogBodyLevelButtonLevelOptinDeclineAll']";
    private final String DEPARTURE = "//*[@id='departure-place']";
    private final String DEPARTURE_OPTION_1 = "//*[@id='react-autocomplete-departure-place-1']";
    private final String ARRIVAL = "//*[@id='arrival-place']";
    private final String ARRIVAL_OPTION_1 = "//*[@id='react-autocomplete-arrival-place-1']";
    private final String SEARCH = "//button[@title='Rechercher un itinéraire']";
    private final String RESULTS = "//section//ul/li";

    private WebDriver driver;

    @BeforeEach
    public void beforeEach() {
        System.setProperty("webdriver.gecko.driver", "c:/dev/tools/selenium/geckodriver.exe");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary("C:/Program Files/Mozilla Firefox/firefox.exe");
        driver = new FirefoxDriver(firefoxOptions);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(50));
        driver.manage().window().maximize();
    }

    @AfterEach
    public void afterEach() {
        driver.quit();
    }

    @Test
    @Tag("NativeSelenium")
    public void seleniumSncfTest() {
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

    @Test
    @Tag("InteractionsWebDriver")
    public void weSncfTest() {
        Interactions interactions = new Interactions(driver);
        interactions.driver.navigate().to("http://www.sncf.com/fr");
        interactions.element
                .at(REFUSE_ALL).click()
                .at(DEPARTURE).sendKeys("Paris")
                .at(DEPARTURE_OPTION_1).click()
                .at(ARRIVAL).sendKeys("Lyon")
                .at(ARRIVAL_OPTION_1).click()
                .at(SEARCH).click()
                .at(RESULTS).waitToBePresent();
    }

    @Test
    @Tag("InteractionsJS")
    public void jsSncfTest() {
        Interactions interactions = new Interactions(driver);
        interactions.convertLocatorTypeToBy(false);
        interactions.driver.navigate().to("http://www.sncf.com/fr");
        interactions.element
                .at(REFUSE_ALL).clickEvent()
                .at(DEPARTURE).sendKeys("Paris")
                .at(DEPARTURE_OPTION_1).clickEvent()
                .at(ARRIVAL).sendKeys("Lyon")
                .at(ARRIVAL_OPTION_1).clickEvent()
                .at(SEARCH).clickEvent()
                .at(RESULTS).waitToBePresent();
    }
}
