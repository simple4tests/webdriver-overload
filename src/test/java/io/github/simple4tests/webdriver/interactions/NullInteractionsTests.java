package io.github.simple4tests.webdriver.interactions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class NullInteractionsTests {

    private WebDriver driver;

    @AfterEach
    public void afterEach() {
        driver.quit();
    }

    @RepeatedTest(2)
    @Tag("Interactions")
    public void interactions_GoogleNullTest(RepetitionInfo info) {
        driver = _TechActions.initChromeDriver();

        Interactions interactions = new Interactions(driver);
        if (2 == info.getCurrentRepetition()) {
            System.out.println("***** convertLocatorTypeToBy = false *****");
            interactions.convertAllLocatorsToBy(false);
        }

        interactions.driver.navigate().to("http://www.google.fr");
        interactions
                .click(null)
                .sendKeys(null, "A")
                .sendKeys("//*", (CharSequence) null)
                .sendKeys(null, (CharSequence) null)
                .sendKeys(null, null, Keys.ENTER);
    }

    @RepeatedTest(2)
    @Tag("InteractionsJS")
    public void interactionsJS_GoogleNullTest(RepetitionInfo info) {
        driver = _TechActions.initChromeDriver();

        Interactions interactions = new Interactions(driver);
        if (2 == info.getCurrentRepetition()) {
            System.out.println("***** convertLocatorTypeToBy = false *****");
            interactions.convertAllLocatorsToBy(false);
        }

        interactions.driver.navigate().to("http://www.google.fr");
        interactions
                .clickEvent(null)
                .set(null, "value", "A")
                .set("//*", null, "A")
                .set("//*", "value", null)
                .set(null, null, null);
    }
}
