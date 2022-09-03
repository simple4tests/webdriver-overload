package io.github.simple4tests.webdriver.interactions;

import io.github.simple4tests.webdriver.utils.TechnicalActions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class GoogleChromeTests {

    private final String ACCEPT = "//*[@id='L2AGLb']";
    private final String SEARCH_CRITERIA = "//input[@name='q']";
    private final String TOTO_UNIVERSAL_MUSIC = "//h3[normalize-space(.)='Toto - Universal Music France']";
    private final String TOTO = "//a[contains(.,'Toto')]";

    private WebDriver driver;

    @BeforeEach
    public void beforeEach() {
        System.setProperty("webdriver.chrome.driver", "c:/dev/tools/selenium/chromedriver.exe");
        driver = new ChromeDriver();
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
        TechnicalActions.waitElementToBePresent(driver, By.xpath(TOTO_UNIVERSAL_MUSIC));
        driver.findElement(By.xpath(TOTO_UNIVERSAL_MUSIC)).click();
        TechnicalActions.scrollIntoView(driver, By.xpath(TOTO));
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
        Interactions interactions = new Interactions(driver);
        interactions.driver.navigate().to("http://www.google.fr");
        interactions
                .click(By.xpath(ACCEPT))
                .sendKeys(By.xpath(SEARCH_CRITERIA), "toto universal music", Keys.ENTER)
                .click(By.xpath(TOTO_UNIVERSAL_MUSIC))
                .click(By.xpath(TOTO))
                .switchToTab(1);
    }

    @Test
    @Tag("InteractionsJS")
    public void jsGoogleTest() {
        Interactions interactions = new Interactions(driver);
        interactions.driver.navigate().to("http://www.google.fr");
        interactions.element
                .locatedByXpath(ACCEPT).clickEvent()
                .locatedByXpath(SEARCH_CRITERIA).set("value", "toto universal music")
                .locatedByXpath(SEARCH_CRITERIA).sendKeys(Keys.ENTER)
                .locatedByXpath(TOTO_UNIVERSAL_MUSIC).clickEvent()
                .locatedByXpath(TOTO).clickEvent();
        interactions.browser.switchToTab(1);
    }
}
