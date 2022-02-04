package io.github.simple4tests.webdriver.providers;

import io.github.simple4tests.webdriver.utils.Yml;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.util.List;
import java.util.Map;

public class FirefoxOptionsProvider {

    public static String OPTIONS_DEFAULT = "firefox_options_default.yml";
    public static String OPTIONS_HEADLESS = "firefox_options_headless.yml";

    public static class OptionsData {
        public List<String> arguments = List.of();
        public Map<String, Object> capabilities = Map.of();
        public Map<String, Object> preferences = Map.of();
        public Profile profile = new Profile();

        public static class Profile {
            public Map<String, Object> preferences = Map.of();
        }
    }

    public static FirefoxOptions get(String optionsAsYamlResource) {
        if (null == optionsAsYamlResource || optionsAsYamlResource.isEmpty()) {
            return get();
        }
        return get(Yml.loadAs(optionsAsYamlResource, OptionsData.class));
    }

    public static FirefoxOptions get() {
        return get(Yml.loadAs(OPTIONS_DEFAULT, OptionsData.class));
    }

    public static FirefoxOptions get(OptionsData optionsData) {

        FirefoxOptions options = new FirefoxOptions();

        options.addArguments(optionsData.arguments);

        for (Map.Entry<String, Object> capability : optionsData.capabilities.entrySet()) {
            options.setCapability(capability.getKey(), capability.getValue());
        }

        for (Map.Entry<String, Object> preference : optionsData.preferences.entrySet()) {
            setPreference(options, "addPreference", preference);
        }

        FirefoxProfile profile = new FirefoxProfile();
        for (Map.Entry<String, Object> preference : optionsData.profile.preferences.entrySet()) {
            setPreference(profile, "setPreference", preference);
        }
        options.setProfile(profile);

        return options;
    }

    private static void setPreference(Object object, String methodName, Map.Entry<String, Object> preference) {
        try {
            switch (preference.getValue().getClass().getSimpleName()) {
                case "Boolean":
                    object.getClass().getDeclaredMethod(methodName, String.class, boolean.class)
                            .invoke(object, preference.getKey(), (boolean) preference.getValue());
                    break;
                case "Integer":
                    object.getClass().getDeclaredMethod(methodName, String.class, int.class)
                            .invoke(object, preference.getKey(), (int) preference.getValue());
                    break;
                case "Double":
                    object.getClass().getDeclaredMethod(methodName, String.class, int.class)
                            .invoke(object, preference.getKey(), ((Double) preference.getValue()).intValue());
                    break;
                case "String":
                default:
                    object.getClass().getDeclaredMethod(methodName, String.class, String.class)
                            .invoke(object, preference.getKey(), preference.getValue().toString());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
