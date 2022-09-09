package io.github.simple4tests.webdriver.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class _TechActions {

    public static WebDriver initChromeDriver() {
        System.out.println("***** USING CHROME DRIVER *****");
        System.setProperty("webdriver.chrome.driver", "c:/dev/tools/selenium/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(50));
        driver.manage().window().maximize();
        return driver;
    }

    public static WebDriver initFirefoxDriver() {
        System.out.println("***** USING GECKO DRIVER *****");
        System.setProperty("webdriver.gecko.driver", "c:/dev/tools/selenium/geckodriver.exe");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary("C:/Program Files/Mozilla Firefox/firefox.exe");
        WebDriver driver = new FirefoxDriver(firefoxOptions);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(50));
        driver.manage().window().maximize();
        return driver;
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            System.out.println("Failed to sleep(millis): " + e);
        }
    }

    public static void scrollIntoView(WebDriver driver, By by) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(by));
    }

    public static void waitElementToBePresent(WebDriver driver, By by) {
        new FluentWait<>(driver)
                .pollingEvery(Duration.ofMillis(250))
                .withTimeout(Duration.ofSeconds(30))
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public static void waitElementToBeVisible(WebDriver driver, By by) {
        new FluentWait<>(driver)
                .pollingEvery(Duration.ofMillis(250))
                .withTimeout(Duration.ofSeconds(30))
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.visibilityOf(driver.findElement(by)));
    }

    public static void waitTabToBePresent(WebDriver driver, int index) {
        new FluentWait<>(driver)
                .pollingEvery(Duration.ofMillis(250))
                .withTimeout(Duration.ofSeconds(30))
                .ignoring(NoSuchElementException.class)
                .until(input -> index < driver.getWindowHandles().size());
    }
}
