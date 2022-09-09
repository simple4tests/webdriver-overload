package io.github.simple4tests.webdriver.interactions;

import io.github.simple4tests.webdriver.utils._TechActions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

public class TheLabTests {

    private final String MENU_IS_CLOSED = "//button[@aria-label='Menu' and @aria-expanded='false']";
    private final String COLLECT_KITTENS = "//a[.='Collecting kittens']";
    private final String START_GAME = "//button[text()='Start Game']";
    private final String GAME_OVER = "//*[text()='Game Over!']";
    private final String KITTENS = "//img[@alt='Cat']";

    private WebDriver driver;

    @AfterEach
    public void afterEach() {
        driver.quit();
    }

    @RepeatedTest(2)
    @Tag("Selenium")
    public void selenium_KittensTest(RepetitionInfo info) {
        if (1 == info.getCurrentRepetition()) driver = _TechActions.initChromeDriver();
        else driver = _TechActions.initFirefoxDriver();

        driver.navigate().to("http://thelab.boozang.com/");
        driver.findElement(By.xpath(MENU_IS_CLOSED)).click();
        _TechActions.waitElementToBeVisible(driver, By.xpath(COLLECT_KITTENS));
        driver.findElement(By.xpath(COLLECT_KITTENS)).click();
        driver.findElement(By.xpath(START_GAME)).click();
        while (0 == driver.findElements(By.xpath(GAME_OVER)).size()) {
            _TechActions.sleep(50);
            if (0 < driver.findElements(By.xpath(KITTENS)).size()
                    && 0 == driver.findElements(By.xpath(GAME_OVER)).size()) {
                try {
                    driver.findElement(By.xpath(KITTENS)).click();
                } catch (WebDriverException ignored) {
                }
            }
        }
    }

    @RepeatedTest(4)
    @Tag("Interactions")
    public void interactions_KittensTest(RepetitionInfo info) {
        if (info.getCurrentRepetition() < 3) driver = _TechActions.initChromeDriver();
        else driver = _TechActions.initFirefoxDriver();

        Interactions interactions = new Interactions(driver);
        if (2 == info.getCurrentRepetition() || 4 == info.getCurrentRepetition()) {
            System.out.println("***** convertLocatorTypeToBy = false *****");
            interactions.convertAllLocatorsToBy(false);
        }

        interactions.driver.navigate().to("http://thelab.boozang.com/");
        interactions
                .click(MENU_IS_CLOSED)
                .click(COLLECT_KITTENS)
                .click(START_GAME);
        while (interactions.isAbsent(GAME_OVER)) {
            interactions.sleep(50);
            if (interactions.isPresent(KITTENS)
                    && interactions.isAbsent(GAME_OVER)) {
                try {
                    interactions.click(KITTENS);
                } catch (WebDriverException ignored) {
                }
            }
        }
    }

    @RepeatedTest(4)
    @Tag("InteractionsJS")
    public void interactionsJS_KittensTest(RepetitionInfo info) {
        if (info.getCurrentRepetition() < 3) driver = _TechActions.initChromeDriver();
        else driver = _TechActions.initFirefoxDriver();

        Interactions interactions = new Interactions(driver);
        if (2 == info.getCurrentRepetition() || 4 == info.getCurrentRepetition()) {
            System.out.println("***** convertLocatorTypeToBy = false *****");
            interactions.convertAllLocatorsToBy(false);
        }

        interactions.driver.navigate().to("http://thelab.boozang.com/");
        interactions
                .clickEvent(MENU_IS_CLOSED)
                .clickEvent(COLLECT_KITTENS).sleep(250)
                .clickEvent(START_GAME);
        while (interactions.isAbsent(GAME_OVER)) {
            interactions.sleep(50);
            if (interactions.isPresent(KITTENS)
                    && interactions.isAbsent(GAME_OVER)) {
                try {
                    interactions.click(KITTENS);
                } catch (WebDriverException ignored) {
                }
            }
        }
    }
}
