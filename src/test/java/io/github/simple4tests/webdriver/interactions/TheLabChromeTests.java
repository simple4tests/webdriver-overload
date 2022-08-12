package io.github.simple4tests.webdriver.interactions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class TheLabChromeTests {

    private final String MENU_OPEN = "//button[@aria-label='Menu' and @aria-expanded='false']";
    private final String COLLECT_KITTENS = "//a[.='Collecting kittens']";
    private final String STAR_GAME = "//button[text()='Start Game']";
    private final String GAME_OVER = "//*[text()='Game Over!']";
    private final String KITTENS = "//img[@alt='Cat']";

    private WebDriver driver;

    @BeforeEach
    public void beforeEach() {
        System.setProperty("webdriver.chrome.driver", "c:/dev/tools/selenium/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(50));
        driver.manage().window().maximize();
    }

    @AfterEach
    public void afterEach() {
        driver.quit();
    }

    @Test
    @Tag("NativeSelenium")
    public void seleniumKittensTest() {
        driver.navigate().to("http://thelab.boozang.com/");
        driver.findElement(By.xpath(MENU_OPEN)).click();
        new FluentWait<>(driver)
                .pollingEvery(Duration.ofMillis(50))
                .withTimeout(Duration.ofSeconds(10))
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(COLLECT_KITTENS))));
        driver.findElement(By.xpath(COLLECT_KITTENS)).click();
        driver.findElement(By.xpath(STAR_GAME)).click();
        while (0 == driver.findElements(By.xpath(GAME_OVER)).size()) {
            try {
                Thread.sleep(50);
            } catch (Exception e) {
                System.out.println("Failed to sleep(millis): " + e);
            }
            if (0 < driver.findElements(By.xpath(KITTENS)).size()
                    && 0 == driver.findElements(By.xpath(GAME_OVER)).size()) {
                try {
                    driver.findElement(By.xpath(KITTENS)).click();
                } catch (WebDriverException ignored) {
                }
            }
        }
    }

    @Test
    @Tag("InteractionsWebDriver")
    public void weKittensTest() {
        RElement element = new RElement(driver);
        driver.navigate().to("http://thelab.boozang.com/");
        element.locatedBy(By.xpath(MENU_OPEN)).click();
        element.locatedBy(By.xpath(COLLECT_KITTENS)).click();
        element.locatedBy(By.xpath(STAR_GAME)).click();
        while (0 == element.locatedBy(By.xpath(GAME_OVER)).count()) {
            if (element.locatedBy(By.xpath(KITTENS)).waitToBePresent(true)
                    && element.locatedBy(By.xpath(GAME_OVER)).isAbsent()) {
                try {
                    element.locatedBy(By.xpath(KITTENS)).click();
                } catch (WebDriverException ignored) {
                }
            }
        }
    }

    @Test
    @Tag("InteractionsJS")
    public void jsKittensTest() {
        RElement element = new RElement(driver);
        driver.navigate().to("http://thelab.boozang.com/");
        element.locatedByXpath(MENU_OPEN).click();
        element.locatedByXpath(COLLECT_KITTENS).click();
        element.locatedByXpath(STAR_GAME).click();
        while (0 == element.locatedByXpath(GAME_OVER).count()) {
            if (element.locatedByXpath(KITTENS).waitToBePresent(true)
                    && element.locatedByXpath(GAME_OVER).isAbsent()) {
                try {
                    element.locatedByXpath(KITTENS).click();
                } catch (WebDriverException ignored) {
                }
            }
        }
    }
}
