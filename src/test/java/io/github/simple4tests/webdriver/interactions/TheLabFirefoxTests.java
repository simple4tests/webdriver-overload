package io.github.simple4tests.webdriver.interactions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public class TheLabFirefoxTests {

    private final String MENU_OPEN = "//button[@aria-label='Menu' and @aria-expanded='false']";
    private final String COLLECT_KITTENS = "//a[.='Collecting kittens']";
    private final String STAR_GAME = "//button[text()='Start Game']";
    private final String GAME_OVER = "//*[text()='Game Over!']";
    private final String KITTENS = "//img[@alt='Cat']";

    private WebDriver driver;

    @BeforeEach
    public void before() {
        System.setProperty("webdriver.gecko.driver", "c:/dev/tools/selenium/geckodriver.exe");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary("C:/Program Files/Mozilla Firefox/firefox.exe");
        driver = new FirefoxDriver(firefoxOptions);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(50));
        driver.manage().window().maximize();
    }

    @AfterEach
    public void after() {
        driver.quit();
    }

    @Test
    @Tag("WebElement")
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
    @Tag("JS")
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
