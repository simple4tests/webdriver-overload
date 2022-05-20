package io.github.simple4tests.webdriver;

import io.github.simple4tests.webdriver.framework.DefaultDriverProvider;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.nio.file.Paths;

public class ProvidersTests {

    @Test
    public void tests() {
        WebDriver driver = DefaultDriverProvider.get("chrome", Paths.get("c:/dev_tools/selenium/chromedriver.exe"), "chrome_options_default.yml");
//        WebDriver driver = DefaultDriverProvider.get("firefox", Paths.get("c:/dev_tools/selenium/geckodriver.exe"), "firefox_options_default.yml");
//        WebDriver driver = DefaultDriverProvider.get("firefox", Paths.get("c:/dev_tools/selenium/geckodriver.exe"), "firefox_options_headless.yml");
        driver.navigate().to("http://www.vd.ch");
        driver.quit();
    }
}
