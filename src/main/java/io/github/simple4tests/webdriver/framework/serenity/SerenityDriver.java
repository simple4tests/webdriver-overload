package io.github.simple4tests.webdriver.framework.serenity;

import io.github.simple4tests.webdriver.framework.DriverProvider;
import net.thucydides.core.webdriver.DriverSource;
import org.openqa.selenium.WebDriver;

import java.net.URL;
import java.nio.file.Paths;

public class SerenityDriver implements DriverSource {

    @Override
    public WebDriver newDriver() {

        if (null == System.getProperty("s4t.browser")) return null;

        if (null != System.getProperty("s4t.driverPath") && !System.getProperty("s4t.driverPath").isEmpty()) {
            try {
                return DriverProvider.get(
                        System.getProperty("s4t.browser"),
                        Paths.get(System.getProperty("s4t.driverPath")),
                        System.getProperty("s4t.optionsAsYamlResource"));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        if (null != System.getProperty("s4t.gridUrl") && !System.getProperty("s4t.gridUrl").isEmpty()) {
            try {
                return DriverProvider.get(
                        System.getProperty("s4t.browser"),
                        new URL(System.getProperty("s4t.gridUrl")),
                        System.getProperty("s4t.optionsAsYamlResource"));
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
