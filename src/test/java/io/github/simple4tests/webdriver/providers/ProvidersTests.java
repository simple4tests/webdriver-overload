package io.github.simple4tests.webdriver.providers;

import io.github.simple4tests.webdriver.framework.DriverProvider;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.nio.file.Paths;

public class ProvidersTests {

    @Test
    public void chromeTests() {
        WebDriver driver = DriverProvider.get("chrome", Paths.get("c:/dev/tools/selenium/chromedriver.exe"), "chrome_options_default.yml");
        driver.navigate().to("http://www.google.com");
        driver.quit();
    }

    @Test
    public void chromeHeadlessTests() {
        WebDriver driver = DriverProvider.get("chrome", Paths.get("c:/dev/tools/selenium/chromedriver.exe"), "chrome_options_headless.yml");
        driver.navigate().to("http://www.google.com");
        driver.quit();
    }

    @Test
    public void firefoxTests() {
        WebDriver driver = DriverProvider.get("firefox", Paths.get("c:/dev/tools/selenium/geckodriver.exe"), "firefox_options_default.yml");
        driver.navigate().to("http://www.google.com");
        driver.quit();
    }

    @Test
    public void firefoxHeadlessTests() {
        WebDriver driver = DriverProvider.get("firefox", Paths.get("c:/dev/tools/selenium/geckodriver.exe"), "firefox_options_headless.yml");
        driver.navigate().to("http://www.google.com");
        driver.quit();
    }
}
