package io.github.simple4tests.webdriver.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class TechnicalActions {

    public static void scrollIntoView(WebDriver driver, By by) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(by));
    }

    public static void waitElementToBePresent(WebDriver driver, By by) {
        new FluentWait<>(driver)
                .pollingEvery(Duration.ofMillis(250))
                .withTimeout(Duration.ofSeconds(10))
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public static void waitElementToBeVisible(WebDriver driver, By by) {
        new FluentWait<>(driver)
                .pollingEvery(Duration.ofMillis(250))
                .withTimeout(Duration.ofSeconds(10))
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.visibilityOf(driver.findElement(by)));
    }
}
