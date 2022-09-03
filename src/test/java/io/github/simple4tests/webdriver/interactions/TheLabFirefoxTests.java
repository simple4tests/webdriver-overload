package io.github.simple4tests.webdriver.interactions;

import io.github.simple4tests.webdriver.utils._TechActions;
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
    public void seleniumKittensTest() {
        driver.navigate().to("http://thelab.boozang.com/");
        driver.findElement(By.xpath(MENU_OPEN)).click();
        _TechActions.waitElementToBeVisible(driver, By.xpath(COLLECT_KITTENS));
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
        Interactions interactions = new Interactions(driver);
        interactions.driver.navigate().to("http://thelab.boozang.com/");
        interactions.element
                .at(MENU_OPEN).click()
                .at(COLLECT_KITTENS).click()
                .at(STAR_GAME).click();
        while (interactions.element.at(GAME_OVER).isAbsent()) {
            if (interactions.element.at(KITTENS).waitToBePresent(true)
                    && interactions.element.at(GAME_OVER).isAbsent()) {
                try {
                    interactions.element.at(KITTENS).click();
                } catch (WebDriverException ignored) {
                }
            }
        }
    }

    @Test
    @Tag("InteractionsJS")
    public void jsKittensTest() {
        Interactions interactions = new Interactions(driver);
        interactions.driver.navigate().to("http://thelab.boozang.com/");
        interactions.element
                .at(MENU_OPEN).click()
                .at(COLLECT_KITTENS).click()
                .at(STAR_GAME).click();
        while (interactions.element.at(GAME_OVER).isAbsent()) {
            if (interactions.element.at(KITTENS).waitToBePresent(true)
                    && interactions.element.at(GAME_OVER).isAbsent()) {
                try {
                    interactions.element.at(KITTENS).click();
                } catch (WebDriverException ignored) {
                }
            }
        }
    }
}
