package io.github.simple4tests.webdriver.integration;

import io.github.simple4tests.webdriver.providers.WebDriverProvider;
import net.thucydides.core.webdriver.DriverSource;
import org.openqa.selenium.WebDriver;

import java.net.URL;
import java.nio.file.Paths;

public class SerenityDriver implements DriverSource {

    @Override
    public WebDriver newDriver() {
        if (null == System.getProperty("browser")) return null;
        if (null != System.getProperty("driverPath") && !System.getProperty("driverPath").isEmpty()) {
            try {
                return WebDriverProvider.get(
                        System.getProperty("browser"),
                        Paths.get(System.getProperty("driverPath")),
                        System.getProperty("optionsAsYamlResource"));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        if (null != System.getProperty("gridUrl") && !System.getProperty("gridUrl").isEmpty()) {
            try {
                return WebDriverProvider.get(
                        System.getProperty("browser"),
                        new URL(System.getProperty("gridUrl")),
                        System.getProperty("optionsAsYamlResource"));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public boolean takesScreenshots() {
        return true;
    }
}
