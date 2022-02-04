package io.github.simple4tests.webdriver.utils;

public class Sleeper {

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            System.out.println("sleep(millis): " + e);
        }
    }
}
