package io.github.simple4tests.webdriver.interactions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class SncfChromeTests {

    private final String REFUSE_ALL = "//*[@id='CybotCookiebotDialogBodyLevelButtonLevelOptinDeclineAll']";
    private final String DEPARTURE = "//*[@id='departure-place']";
    private final String DEPARTURE_OPTION_1 = "//*[@id='react-autocomplete-departure-place-1']";
    private final String ARRIVAL = "//*[@id='arrival-place']";
    private final String ARRIVAL_OPTION_1 = "//*[@id='react-autocomplete-arrival-place-1']";
    private final String SEARCH = "//button[@title='Rechercher un itinéraire']";
    private final String RESULTS = "//section//ul/li";

    private WebDriver driver;

    @BeforeEach
    public void before() {
        System.setProperty("webdriver.chrome.driver", "c:/dev/tools/selenium/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(50));
        driver.manage().window().maximize();
    }

    @AfterEach
    public void after() {
        driver.quit();
    }

    @Test
    @Tag("WebElement")
    public void weSncfTest() {
        Interactions interactions = new Interactions(driver);
        interactions.driver.navigate().to("http://www.sncf.com/fr");
        interactions.element
                .locatedBy(By.xpath(REFUSE_ALL)).click()
                .locatedBy(By.xpath(DEPARTURE)).sendKeys("Paris")
                .locatedBy(By.xpath(DEPARTURE_OPTION_1)).click()
                .locatedBy(By.xpath(ARRIVAL)).sendKeys("Lyon")
                .locatedBy(By.xpath(ARRIVAL_OPTION_1)).click()
                .locatedBy(By.xpath(SEARCH)).click()
                .locatedBy(By.xpath(RESULTS)).waitToBePresent();
    }

    @Test
    @Tag("JS")
    public void jsSncfTest() {
        Interactions interactions = new Interactions(driver);
        interactions.driver.navigate().to("http://www.sncf.com/fr");
        interactions.element
                .locatedByXpath(REFUSE_ALL).clickEvent()
                .locatedByXpath(DEPARTURE).sendKeys("Paris")
                .locatedByXpath(DEPARTURE_OPTION_1).clickEvent()
                .locatedByXpath(ARRIVAL).sendKeys("Lyon")
                .locatedByXpath(ARRIVAL_OPTION_1).clickEvent()
                .locatedByXpath(SEARCH).clickEvent()
                .locatedByXpath(RESULTS).waitToBePresent();
    }
}
