package io.github.simple4tests.webdriver.interactions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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
        _TechActions.waitElementToBePresent(driver, By.xpath(MENU_IS_CLOSED));
        driver.findElement(By.xpath(START_GAME)).click();
        while (driver.findElements(By.xpath(GAME_OVER)).isEmpty()) {
            _TechActions.sleep(50);
            if (!driver.findElements(By.xpath(KITTENS)).isEmpty()
                    && driver.findElements(By.xpath(GAME_OVER)).isEmpty()) {
                try {
                    driver.findElement(By.xpath(KITTENS)).click();
                } catch (Exception ignored) {
                }
            }
        }
    }

    @RepeatedTest(4)
    @Tag("Interactions")
    public void interactions_KittensTest(RepetitionInfo info) {
        if (info.getCurrentRepetition() < 3) driver = _TechActions.initChromeDriver();
        else driver = _TechActions.initFirefoxDriver();

        Interactions ui = new Interactions(driver);
        if (2 == info.getCurrentRepetition() || 4 == info.getCurrentRepetition()) {
            System.out.println("***** convertLocatorTypeToBy = false *****");
            ui.convertAllLocatorsToBy(false);
        }
        ui.setAutoScroll(false);

        ui.driver.navigate().to("http://thelab.boozang.com/");
        ui
                .click(MENU_IS_CLOSED)
                .click(COLLECT_KITTENS)
                .waitToBePresent(MENU_IS_CLOSED)
                .click(START_GAME);
        while (ui.isAbsent(GAME_OVER)) {
            ui.sleep(50);
            if (ui.isPresent(KITTENS)
                    && ui.isAbsent(GAME_OVER)) {
                try {
                    ui.click(KITTENS);
                } catch (Exception ignored) {
                }
            }
        }
    }

    @RepeatedTest(4)
    @Tag("InteractionsJS")
    public void interactionsJS_KittensTest(RepetitionInfo info) {
        Interactions ui;
        switch (info.getCurrentRepetition()) {
            case 1:
                driver = _TechActions.initChromeDriver();
                ui = new Interactions(driver);
                break;
            case 2:
                driver = _TechActions.initChromeDriver();
                ui = new Interactions(driver);
                System.out.println("***** convertLocatorTypeToBy = false *****");
                ui.convertAllLocatorsToBy(false);
                break;
            case 3:
                driver = _TechActions.initFirefoxDriver();
                ui = new Interactions(driver);
                ui.setImplicitWaits(100, 100);
                break;
            case 4:
                driver = _TechActions.initFirefoxDriver();
                ui = new Interactions(driver);
                System.out.println("***** convertLocatorTypeToBy = false *****");
                ui.setImplicitWaits(100, 100);
                ui.convertAllLocatorsToBy(false);
                break;
            default:
                return;
        }
        ui.setAutoScroll(false);

        ui.driver.navigate().to("http://thelab.boozang.com/");
        ui
                .clickEvent(MENU_IS_CLOSED)
                .clickEvent(COLLECT_KITTENS)
                .waitToBePresent(MENU_IS_CLOSED)
                .clickEvent(START_GAME);
        ui.setImplicitWaits(0);
        while (ui.isAbsent(GAME_OVER)) {
            ui.sleep(50);
            if (ui.isPresent(KITTENS)
                    && ui.isAbsent(GAME_OVER)) {
                try {
                    ui.clickEvent(KITTENS);
                } catch (Exception ignored) {
                }
            }
        }
    }

    @RepeatedTest(4)
    @Tag("InteractionsActions")
    public void interactionsActions_KittensTest(RepetitionInfo info) {
        Interactions ui;
        switch (info.getCurrentRepetition()) {
            case 1:
                driver = _TechActions.initChromeDriver();
                ui = new Interactions(driver);
                ui.setImplicitWaits(50, 50);
                break;
            case 2:
                driver = _TechActions.initChromeDriver();
                ui = new Interactions(driver);
                System.out.println("***** convertLocatorTypeToBy = false *****");
                ui.setImplicitWaits(50, 50);
                ui.convertAllLocatorsToBy(false);
                break;
            case 3:
                driver = _TechActions.initFirefoxDriver();
                ui = new Interactions(driver);
                ui.setImplicitWaits(50, 50);
                break;
            case 4:
                driver = _TechActions.initFirefoxDriver();
                ui = new Interactions(driver);
                System.out.println("***** convertLocatorTypeToBy = false *****");
                ui.setImplicitWaits(50, 50);
                ui.convertAllLocatorsToBy(false);
                break;
            default:
                return;
        }
        ui.setAutoScroll(false);

        ui.driver.navigate().to("http://thelab.boozang.com/");
        ui
                .actionsClick(MENU_IS_CLOSED)
                .actionsClick(COLLECT_KITTENS)
                .waitToBePresent(MENU_IS_CLOSED)
                .actionsClick(START_GAME);
        ui.setImplicitWaits(0);
        while (ui.isAbsent(GAME_OVER)) {
            ui.sleep(50);
            if (ui.isPresent(KITTENS)
                    && ui.isAbsent(GAME_OVER)) {
                try {
                    ui.actionsClick(KITTENS);
                } catch (Exception ignored) {
                }
            }
        }
    }
}
