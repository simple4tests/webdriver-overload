package io.github.simple4tests.webdriver.providers;

import io.github.simple4tests.webdriver.utils.Substitutor;
import io.github.simple4tests.webdriver.utils.Yml;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.File;
import java.util.List;
import java.util.Map;

public class FirefoxOptionsProvider {

    public static String OPTIONS_DEFAULT = "firefox_options_default.yml";
    public static String OPTIONS_HEADLESS = "firefox_options_headless.yml";

    public static class Options {
        public String binary = "";
        public List<String> arguments = List.of();
        public Map<String, Object> capabilities = Map.of();
        public Map<String, Object> preferences = Map.of();
        public Profile profile = new Profile();

        public void normalize() {
            binary = Substitutor.normalizePath(binary);
            arguments = Substitutor.replaceSystemProperties(arguments);
            capabilities = Substitutor.replaceSystemProperties(capabilities);
            preferences = Substitutor.replaceSystemProperties(preferences);
            profile.normalize();
        }

        public static class Profile {
            public Map<String, Object> preferences = Map.of();
            public List<String> extensions = List.of();

            public void normalize() {
                preferences = Substitutor.replaceSystemProperties(preferences);
                extensions = Substitutor.normalizePath(extensions);
            }
        }
    }

    public static FirefoxOptions get(String optionsAsYamlResource) {
        if (null == optionsAsYamlResource || optionsAsYamlResource.isEmpty()) {
            return get();
        }
        return get(Yml.loadAs(optionsAsYamlResource, Options.class));
    }

    public static FirefoxOptions get() {
        return get(Yml.loadAs(OPTIONS_DEFAULT, Options.class));
    }

    public static FirefoxOptions get(Options options) {
        FirefoxOptions firefoxOptions = new FirefoxOptions();

        if (null == options) {
            return firefoxOptions;
        }

        options.normalize();

        if (!options.binary.isEmpty()) {
            firefoxOptions.setBinary(options.binary);
        }

        firefoxOptions.addArguments(options.arguments);

        options.capabilities.forEach(firefoxOptions::setCapability);

        options.preferences.forEach(firefoxOptions::addPreference);

        firefoxOptions.setProfile(getFirefoxProfile(options.profile));

        return firefoxOptions;
    }

    private static FirefoxProfile getFirefoxProfile(Options.Profile profile) {
        FirefoxProfile firefoxProfile = new FirefoxProfile();

        profile.preferences.forEach(firefoxProfile::setPreference);

        profile.extensions.stream().map(File::new).forEach(firefoxProfile::addExtension);

        return firefoxProfile;
    }
}
