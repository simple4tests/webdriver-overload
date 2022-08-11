package io.github.simple4tests.webdriver.interactions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public class GoogleFirefoxTests {

    private final String ACCEPT = "//*[@id='L2AGLb']";
    private final String SEARCH_CRITERIA = "//input[@name='q']";
    private final String TOTO_UNIVERSAL_MUSIC = "//h3[normalize-space(.)='Toto - Universal Music France']";
    private final String TOTO = "//a[contains(.,'Toto')]";

    private WebDriver driver;

    @BeforeEach
    public void before() {
        System.setProperty("webdriver.gecko.driver", "c:/dev/tools/selenium/geckodriver.exe");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary("C:/Program Files/Mozilla Firefox/firefox.exe");
        driver = new FirefoxDriver(firefoxOptions);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(50));
    }

    @AfterEach
    public void after() {
        driver.quit();
    }

    @Test
    @Tag("WebElement")
    public void weGoogleTest() {
        RElement element = new RElement(driver);
        driver.navigate().to("http://www.google.fr");
        element.locatedBy(By.xpath(ACCEPT)).click();
        element.locatedBy(By.xpath(SEARCH_CRITERIA)).sendKeys("toto universal music", Keys.ENTER);
        element.locatedBy(By.xpath(TOTO_UNIVERSAL_MUSIC)).click();
        element.locatedBy(By.xpath(TOTO)).click();
        new RBrowser(driver).switchToTab(1);
    }

    @Test
    @Tag("JS")
    public void jsGoogleTest() {
        RElement element = new RElement(driver);
        driver.navigate().to("http://www.google.fr");
        element.locatedByXpath(ACCEPT).click();
        element.locatedByXpath(SEARCH_CRITERIA).set("value", "toto universal music");
        element.locatedByXpath(SEARCH_CRITERIA).sendKeys(Keys.ENTER);
        element.locatedByXpath(TOTO_UNIVERSAL_MUSIC).click();
        element.locatedByXpath(TOTO).click();
        new RBrowser(driver).switchToTab(1);
    }
}
