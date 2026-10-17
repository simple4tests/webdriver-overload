package io.github.simple4tests.webdriver.providers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.HasExtensions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.nio.file.Path;

public class WebDriverProvider {

    public static WebDriver get(String browser, Path driverPath, String optionsAsYamlResource) {
        if (null == browser || null == driverPath) return null;
        switch (browser.toUpperCase()) {
            case "FIREFOX":
                System.out.println("USING GECKO DRIVER");
                System.setProperty("webdriver.gecko.driver", driverPath.toString());
                FirefoxDriver firefoxDriver = new FirefoxDriver(FirefoxOptionsProvider.get(optionsAsYamlResource));
                FirefoxOptionsProvider.getExtensions(optionsAsYamlResource).stream()
                        .map(java.nio.file.Paths::get)
                        .forEach(firefoxDriver::installExtension);
                return firefoxDriver;
            case "CHROME":
                System.out.println("USING CHROME DRIVER");
                System.setProperty("webdriver.chrome.driver", driverPath.toString());
                return new ChromeDriver(ChromeOptionsProvider.get(optionsAsYamlResource));
            default:
                System.out.println("ONLY FIREFOX AND CHROME DRIVERS ARE SUPPORTED BY DriverProvider");
                return null;
        }
    }

    public static WebDriver get(String browser, URL gridUrl, String optionsAsYamlResource) {
        if (null == browser || null == gridUrl) return null;
        switch (browser.toUpperCase()) {
            case "FIREFOX":
                RemoteWebDriver remoteDriver = new RemoteWebDriver(gridUrl, FirefoxOptionsProvider.get(optionsAsYamlResource));
                WebDriver firefoxDriver = new Augmenter().augment(remoteDriver);
                FirefoxOptionsProvider.getExtensions(optionsAsYamlResource).stream()
                        .map(java.nio.file.Paths::get)
                        .forEach(path -> ((HasExtensions) firefoxDriver).installExtension(path));
                return firefoxDriver;
            case "CHROME":
                return new RemoteWebDriver(gridUrl, ChromeOptionsProvider.get(optionsAsYamlResource));
            default:
                System.out.println("ONLY FIREFOX AND CHROME DRIVERS ARE SUPPORTED BY DriverProvider");
                return null;
        }
    }
}
