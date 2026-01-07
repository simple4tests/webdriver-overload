package io.github.simple4tests.webdriver.reporters;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AssertTests {

    SystemOutReporter reporter = new SystemOutReporter();

    public static class Country {
        public String name;
        public int size;

        public Country(String name, int size) {
            this.name = name;
            this.size = size;
        }

        public String getName() {
            return name;
        }

        public int getSize() {
            return size;
        }
    }

    @Test
    public void checkTest() {

        reporter.clearErrors();

        reporter.startStep("My first action");
        reporter.endStep();

        reporter.startStep("My second action with data");
        reporter.reportData("This is my data for second action");
        reporter.endStep();

        Assertions.assertThat("A STRING")
                .isEqualToIgnoringCase("A string")
                .as("Check a string equals to another string ignoring case");
        reporter.assertThat("Check a string equals to another string ignoring case",
                "A STRING",
                Matchers.equalToIgnoringCase("A string"));
        reporter.groovyAssertThat("Check a string equals to another string ignoring case",
                "A STRING",
                "equalToIgnoringCase('A string')");
        reporter.groovyAssert("Check a string equals to another string ignoring case",
                "'A STRING'.equalsIgnoreCase('A string')");

        Assertions.assertThat("One STRING or TWO")
                .contains("STRING or")
                .as("Check a string contains another string");
        reporter.assertThat("Check a string contains another string",
                "One STRING or TWO",
                Matchers.containsString("STRING or"));
        reporter.groovyAssertThat("Check a string contains another string",
                "One STRING or TWO",
                "containsString('STRING or')");
        reporter.groovyAssert("Check a string contains another string",
                "'One STRING or TWO'.contains('STRING or')");

        Assertions.assertThat("The Lord of the Rings")
                .isNotNull()
                .startsWith("The")
                .contains("Lord")
                .endsWith("Rings")
                .as("Check multiples things in a string");
        reporter.assertThat("Check multiples things in a string",
                "The Lord of the Rings",
                Matchers.allOf(
                        Matchers.notNullValue(),
                        Matchers.startsWith("The"),
                        Matchers.containsString("Lord"),
                        Matchers.endsWith("Rings")));

        Assertions.assertThat(1).isEqualTo(1).as("Check value of an integer");
        reporter.assertThat("Check value of an integer",
                1,
                Matchers.equalTo(1));
        reporter.groovyAssertThat("Check value of an integer",
                "1",
                "equalTo('1')");
        reporter.groovyAssert("Check value of an integer",
                "1.equals(1)");

        Assertions.assertThat(List.of("toto", "titi", "tata"))
                .containsExactlyInAnyOrder("toto", "tata", "titi")
                .as("Check containt of a list");
        reporter.assertThat("Check containt of a list",
                List.of("toto", "titi", "tata"),
                Matchers.containsInAnyOrder("toto", "tata", "titi"));

        List<Country> countries = List.of(
                new Country("Geneve", 20),
                new Country("Lausanne", 25),
                new Country("Zurich", 30));
        Assertions.assertThat(countries)
                .hasSize(3)
                .allMatch(country -> !country.name.isEmpty())
                .allMatch(country -> country.size > 10);
        reporter.assertThat("Vérifier les propriétés d'un objet A",
                countries,
                Matchers.allOf(
                        Matchers.hasSize(3),
                        Matchers.everyItem(Matchers.hasProperty("name", Matchers.not(Matchers.empty()))),
                        Matchers.everyItem(Matchers.hasProperty("size", Matchers.greaterThan(10)))
                ));

        reporter.throwAssertionErrorIfAny(true);
    }
}
