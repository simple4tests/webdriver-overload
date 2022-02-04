package io.github.simple4tests.webdriver.providers;

import org.openqa.selenium.chrome.ChromeOptions;
import io.github.simple4tests.webdriver.utils.Yml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChromeOptionsProvider {

    public static String OPTIONS_DEFAULT = "chrome_options_default.yml";
    public static String OPTIONS_HEADLESS = "chrome_options_headless.yml";

    public static class OptionsData {
        public String binary;
        public List<String> arguments = List.of();
        public Map<String, Object> capabilities = Map.of();
        public List<String> extensions = List.of();
    }

    public static ChromeOptions get(String optionsAsYamlResource) {
        if (null == optionsAsYamlResource || optionsAsYamlResource.isEmpty()) {
            return get();
        }
        return get(Yml.loadAs(optionsAsYamlResource, OptionsData.class));
    }

    public static ChromeOptions get() {
        return get(Yml.loadAs(OPTIONS_DEFAULT, OptionsData.class));
    }

    public static ChromeOptions get(OptionsData optionsData) {
        ChromeOptions options = new ChromeOptions();

        if (null != optionsData.binary && !optionsData.binary.isEmpty()) {
            options.setBinary(optionsData.binary);
        }

        options.addArguments(optionsData.arguments);

        for (Map.Entry<String, Object> capability : optionsData.capabilities.entrySet()) {
            options.setCapability(capability.getKey(), capability.getValue());
        }

        if (null != optionsData.extensions) {
            List<File> extensions = new ArrayList<>();
            for (String extension : optionsData.extensions) {
                extensions.add(new File(extension));
            }
            options.addExtensions(extensions);
        }

        return options;
    }
}
