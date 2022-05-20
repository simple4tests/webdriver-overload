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
        System.setProperty("webdriver.chrome.driver", "c:/dev_tools/selenium/chromedriver.exe");
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
        RElement element = new RElement(driver);
        driver.navigate().to("http://www.sncf.com/fr");
        element.locatedBy(By.xpath(REFUSE_ALL)).click();
        element.locatedBy(By.xpath(DEPARTURE)).sendKeys("Paris");
        element.locatedBy(By.xpath(DEPARTURE_OPTION_1)).click();
        element.locatedBy(By.xpath(ARRIVAL)).sendKeys("Lyon");
        element.locatedBy(By.xpath(ARRIVAL_OPTION_1)).click();
        element.locatedBy(By.xpath(SEARCH)).click();
        element.locatedBy(By.xpath(RESULTS)).waitToBePresent();
    }

    @Test
    @Tag("JS")
    public void jsSncfTest() {
        RElement element = new RElement(driver);
        driver.navigate().to("http://www.sncf.com/fr");
        element.locatedByXpath(REFUSE_ALL).click();
        element.locatedByXpath(DEPARTURE).sendKeys("Paris");
        element.locatedByXpath(DEPARTURE_OPTION_1).click();
        element.locatedByXpath(ARRIVAL).sendKeys("Lyon");
        element.locatedByXpath(ARRIVAL_OPTION_1).click();
        element.locatedByXpath(SEARCH).click();
        element.locatedByXpath(RESULTS).waitToBePresent();
    }
}
