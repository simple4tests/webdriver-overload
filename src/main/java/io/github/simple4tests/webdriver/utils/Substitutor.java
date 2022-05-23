package io.github.simple4tests.webdriver.utils;

import org.apache.commons.lang.text.StrSubstitutor;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Substitutor {

    public static String replaceSystemProperties(String input) {
        return StrSubstitutor.replaceSystemProperties(input);
    }

    public static List<String> replaceSystemProperties(List<String> inputs) {
        return inputs.stream().map(Substitutor::replaceSystemProperties).collect(Collectors.toList());
    }

    public static Object replaceSystemProperties(Object input) {
        return input instanceof String ? replaceSystemProperties((String) input) : input;
    }

    public static Map.Entry<String, Object> replaceSystemProperties(Map.Entry<String, Object> input) {
        return Map.entry(input.getKey(), replaceSystemProperties(input.getValue()));
    }

    public static Map<String, Object> replaceSystemProperties(Map<String, Object> map) {
        return map.entrySet().stream()
                .map(Substitutor::replaceSystemProperties)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static String normalizePath(String input) {
        return input.isEmpty() ? input : Paths.get(replaceSystemProperties(input)).toAbsolutePath().toString();
    }

    public static List<String> normalizePath(List<String> inputs) {
        return inputs.stream().map(Substitutor::normalizePath).collect(Collectors.toList());
    }
}
