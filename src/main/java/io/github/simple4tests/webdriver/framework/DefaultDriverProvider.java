package io.github.simple4tests.webdriver.framework;

import io.github.simple4tests.webdriver.options.ChromeOptionsProvider;
import io.github.simple4tests.webdriver.options.FirefoxOptionsProvider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.nio.file.Path;

public class DefaultDriverProvider {

    public static WebDriver get(String browser, Path driverPath, String optionsAsYamlResource) {
        if (null == browser || null == driverPath) return null;
        switch (browser.toUpperCase()) {
            case "FIREFOX":
                System.out.println("USING GECKO DRIVER");
                System.setProperty("webdriver.gecko.driver", driverPath.toString());
                return new FirefoxDriver(FirefoxOptionsProvider.get(optionsAsYamlResource));
            case "CHROME":
                System.out.println("USING CHROME DRIVER");
                System.setProperty("webdriver.chrome.driver", driverPath.toString());
                return new ChromeDriver(ChromeOptionsProvider.get(optionsAsYamlResource));
            default:
                System.out.println("ONLY FIREFOX OR CHROME DRIVER ARE SUPPORTED BY DefaultDriverProvider");
                return null;
        }
    }

    public static WebDriver get(String browser, URL gridUrl, String optionsAsYamlResource) {
        if (null == browser || null == gridUrl) return null;
        switch (browser.toUpperCase()) {
            case "FIREFOX":
                return new RemoteWebDriver(gridUrl, FirefoxOptionsProvider.get(optionsAsYamlResource));
            case "CHROME":
                return new RemoteWebDriver(gridUrl, ChromeOptionsProvider.get(optionsAsYamlResource));
            default:
                System.out.println("ONLY FIREFOX OR CHROME DRIVER ARE SUPPORTED BY DefaultDriverProvider");
                return null;
        }
    }
}
