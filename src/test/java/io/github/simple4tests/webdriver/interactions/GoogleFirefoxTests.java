package io.github.simple4tests.webdriver.interactions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class GoogleFirefoxTests {

    private final String ACCEPT = "//*[@id='L2AGLb']";
    private final String SEARCH_CRITERIA = "//input[@name='q']";
    private final String TOTO_UNIVERSAL_MUSIC = "//h3[normalize-space(.)='Toto - Universal Music France']";
    private final String TOTO = "//a[contains(.,'Toto')]";

    private WebDriver driver;

    @BeforeEach
    public void beforeEach() {
        System.setProperty("webdriver.gecko.driver", "c:/dev/tools/selenium/geckodriver.exe");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary("C:/Program Files/Mozilla Firefox/firefox.exe");
        driver = new FirefoxDriver(firefoxOptions);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(50));
    }

    @AfterEach
    public void afterEach() {
        driver.quit();
    }

    @Test
    @Tag("NativeSelenium")
    public void seleniumGoogleTest() {
        driver.navigate().to("http://www.google.fr");
        driver.findElement(By.xpath(ACCEPT)).click();
        driver.findElement(By.xpath(SEARCH_CRITERIA)).sendKeys("toto universal music", Keys.ENTER);
        new FluentWait<>(driver)
                .pollingEvery(Duration.ofMillis(250))
                .withTimeout(Duration.ofSeconds(10))
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(TOTO_UNIVERSAL_MUSIC)));
        driver.findElement(By.xpath(TOTO_UNIVERSAL_MUSIC)).click();
//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath(TOTO)));
        driver.findElement(By.xpath(TOTO)).click();
        new FluentWait<>(driver)
                .pollingEvery(Duration.ofMillis(250))
                .withTimeout(Duration.ofSeconds(10))
                .ignoring(NoSuchElementException.class)
                .until(input -> 1 < driver.getWindowHandles().size());
        driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
    }

    @Test
    @Tag("InteractionsWebDriver")
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
    @Tag("InteractionsJS")
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
