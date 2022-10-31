package io.github.simple4tests.webdriver.providers;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.nio.file.Paths;

public class ProvidersTests {

    @Test
    public void chromeTests() {
        WebDriver driver = WebDriverProvider.get("chrome", Paths.get("c:/dev/tools/selenium/chromedriver.exe"), "chrome_options_default.yml");
        driver.navigate().to("http://www.google.com");
        driver.quit();
    }

    @Test
    public void chromeHeadlessTests() {
        WebDriver driver = WebDriverProvider.get("chrome", Paths.get("c:/dev/tools/selenium/chromedriver.exe"), "chrome_options_headless.yml");
        driver.navigate().to("http://www.google.com");
        driver.quit();
    }

    @Test
    public void firefoxTests() {
        WebDriver driver = WebDriverProvider.get("firefox", Paths.get("c:/dev/tools/selenium/geckodriver.exe"), "firefox_options_default.yml");
        driver.navigate().to("http://www.google.com");
        driver.quit();
    }

    @Test
    public void firefoxHeadlessTests() {
        WebDriver driver = WebDriverProvider.get("firefox", Paths.get("c:/dev/tools/selenium/geckodriver.exe"), "firefox_options_headless.yml");
        driver.navigate().to("http://www.google.com");
        driver.quit();
    }
}
