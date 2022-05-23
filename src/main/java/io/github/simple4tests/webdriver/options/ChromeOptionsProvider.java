package io.github.simple4tests.webdriver.options;

import io.github.simple4tests.webdriver.utils.Substitutor;
import io.github.simple4tests.webdriver.utils.Yml;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChromeOptionsProvider {

    public static String OPTIONS_DEFAULT = "chrome_options_default.yml";
    public static String OPTIONS_HEADLESS = "chrome_options_headless.yml";

    public static class Options {
        public String binary = "";
        public List<String> arguments = List.of();
        public Map<String, Object> capabilities = Map.of();
        public Map<String, Object> experimentalOptions = Map.of();
        public List<String> extensions = List.of();

        public void normalize() {
            binary = Substitutor.normalizePath(binary);
            arguments = Substitutor.replaceSystemProperties(arguments);
            capabilities = Substitutor.replaceSystemProperties(capabilities);
            experimentalOptions = Substitutor.replaceSystemProperties(experimentalOptions);
            extensions = Substitutor.normalizePath(extensions);
        }
    }

    public static ChromeOptions get(String optionsAsYamlResource) {
        if (null == optionsAsYamlResource || optionsAsYamlResource.isEmpty()) {
            return get();
        }
        return get(Yml.loadAs(optionsAsYamlResource, Options.class));
    }

    public static ChromeOptions get() {
        return get(Yml.loadAs(OPTIONS_DEFAULT, Options.class));
    }

    public static ChromeOptions get(Options options) {
        ChromeOptions chromeOptions = new ChromeOptions();

        if (null == options) {
            return chromeOptions;
        }

        options.normalize();

        if (!options.binary.isEmpty()) {
            chromeOptions.setBinary(options.binary);
        }

        if (!options.arguments.isEmpty()) {
            chromeOptions.addArguments(options.arguments);
        }

        options.capabilities.forEach(chromeOptions::setCapability);

        if (!options.experimentalOptions.isEmpty()) {
            chromeOptions.setExperimentalOption("prefs", options.experimentalOptions);
        }

        chromeOptions.addExtensions(options.extensions.stream()
                .map(File::new)
                .collect(Collectors.toList()));

        return chromeOptions;
    }
}
