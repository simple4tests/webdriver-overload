package io.github.simple4tests.webdriver.utils;

import org.apache.commons.lang.text.StrSubstitutor;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Substitutor {

    public static String systemProperties(String input) {
        return StrSubstitutor.replaceSystemProperties(input);
    }

    public static List<String> systemProperties(List<String> inputs) {
        return inputs.stream().map(Substitutor::systemProperties).collect(Collectors.toList());
    }

    public static Object systemProperties(Object input) {
        return input instanceof String ? systemProperties((String) input) : input;
    }

    public static Map.Entry<String, Object> systemProperties(Map.Entry<String, Object> input) {
        return Map.entry(input.getKey(), systemProperties(input.getValue()));
    }

    public static Map<String, Object> systemProperties(Map<String, Object> map) {
        return map.entrySet().stream()
                .map(Substitutor::systemProperties)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static String normalizePath(String input) {
        return input.isEmpty() ? input : Paths.get(systemProperties(input)).toAbsolutePath().toString();
    }

    public static List<String> normalizePath(List<String> inputs) {
        return inputs.stream().map(Substitutor::normalizePath).collect(Collectors.toList());
    }
}
