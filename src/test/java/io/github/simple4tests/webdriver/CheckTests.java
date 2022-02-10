package io.github.simple4tests.webdriver;

import io.github.simple4tests.webdriver.reporting.SystemOutReporter;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CheckTests {

    @Data
    @AllArgsConstructor
    public static class Country {
        public String name;
        public int size;
    }

    @Test
    public void checkTest() {

        SystemOutReporter reporter = new SystemOutReporter();

        Assertions.assertThat("One STRING").isEqualToIgnoringCase("One String");
        reporter.addCheck("Vérifier l'égalité à la case près d’une chaîne de caractères A",
                "One STRING",
                Matchers.equalToIgnoringCase("One String"));
        reporter.addGroovyAssertThatCheck("Vérifier l'égalité à la case près d’une chaîne de caractères B",
                "One STRING",
                "equalToIgnoringCase('One String')");
        reporter.addGroovyAssertCheck("Vérifier l'égalité à la case près d’une chaîne de caractères C",
                "'One STRING'.equalsIgnoreCase('One String')");

        Assertions.assertThat("The Lord of the Rings")
                .isNotNull()
                .startsWith("The")
                .contains("Lord")
                .endsWith("Rings");
        reporter.addCheck("Vérifier plusieurs critères sur une chaine de caractères A",
                "The Lord of the Rings",
                Matchers.allOf(
                        Matchers.notNullValue(),
                        Matchers.startsWith("The"),
                        Matchers.containsString("Lord"),
                        Matchers.endsWith("Rings")));

        Assertions.assertThat("One STRING or TWO").contains("STRING or");
        reporter.addCheck("Vérifier le contenu d’une chaîne de caractères A",
                "One STRING or TWO",
                Matchers.containsString("STRING or"));
        reporter.addGroovyAssertThatCheck("Vérifier le contenu d’une chaîne de caractères B",
                "One STRING or TWO",
                "containsString('STRING or')");
        reporter.addGroovyAssertCheck("Vérifier le contenu d’une chaîne de caractères C",
                "'One STRING or TWO'.contains('STRING or')");

        Assertions.assertThat(1).isEqualTo(1);
        reporter.addCheck("Vérifier un entier A",
                1,
                Matchers.equalTo(1));
        reporter.addGroovyAssertThatCheck("Vérifier un entier B",
                "1",
                "equalTo('1')");
        reporter.addGroovyAssertCheck("Vérifier un entier C",
                "1.equals(1)");

        Assertions.assertThat(List.of("toto", "titi", "tata")).containsExactlyInAnyOrder("toto", "tata", "titi");
        reporter.addCheck("Vérifier le contenu d’une liste A",
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
        reporter.addCheck("Vérifier les propriétés d'un objet A",
                countries,
                Matchers.allOf(
                        Matchers.hasSize(3),
                        Matchers.everyItem(Matchers.hasProperty("name", Matchers.not(Matchers.empty()))),
                        Matchers.everyItem(Matchers.hasProperty("size", Matchers.greaterThan(10)))
                ));

        reporter.reportErrors();
    }
}
