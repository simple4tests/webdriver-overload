package io.github.simple4tests.webdriver.utils;

import org.yaml.snakeyaml.Yaml;

public class Yml {

    public static <T> T loadAs(String ressourceFilePath, Class<T> type) {
        return new Yaml().loadAs(
                Thread.currentThread().getContextClassLoader().getResourceAsStream(ressourceFilePath),
                type);
    }
}
