package io.github.simple4tests.webdriver.providers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.nio.file.Path;

public class WebDriverProvider {

    public static WebDriver get(String browser, Path driverPath, String optionsAsYamlResource) {
        if (null == browser) {
            browser = "";
        }
        switch (browser.toUpperCase()) {
            case "FIREFOX":
                System.out.println("USING GECKO DRIVER");
                System.setProperty("webdriver.gecko.driver", driverPath.toString());
                return new FirefoxDriver(FirefoxOptionsProvider.get(optionsAsYamlResource));
            case "CHROME":
            default:
                System.out.println("USING CHROME DRIVER");
                System.setProperty("webdriver.chrome.driver", driverPath.toString());
                return new ChromeDriver(ChromeOptionsProvider.get(optionsAsYamlResource));
        }
    }

    public static WebDriver get(String browser, URL seleniumServer, String optionsAsYamlResource) {
        if (null == browser) {
            browser = "";
        }
        switch (browser.toUpperCase()) {
            case "FIREFOX":
                return new RemoteWebDriver(seleniumServer, FirefoxOptionsProvider.get(optionsAsYamlResource));
            case "CHROME":
            default:
                return new RemoteWebDriver(seleniumServer, ChromeOptionsProvider.get(optionsAsYamlResource));
        }
    }
}
