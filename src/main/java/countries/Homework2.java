package countries;

import java.io.IOException;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.*;

import java.time.ZoneId;
import java.util.stream.Collectors;

public class Homework2 {

    private List<Country> countries;

    public Homework2() {
        countries = new CountryRepository().getAll();
    }

    /**
     * Returns the longest country name translation.
     */
    public Optional<String> streamPipeline1() {
        return countries.stream().map(country -> country.getTranslations()).flatMap(x->x.values().stream()).max(Comparator.comparingInt(x->x.length()));
    }

    /**
     * Returns the longest Italian (i.e., {@code "it"}) country name translation.
     */
    public Optional<String> streamPipeline2() {
        return countries.stream().map(country -> country.getTranslations()).flatMap(x->x.entrySet().stream().filter(y->y.getKey()=="it").map(z->z.getValue())).max(Comparator.comparingInt(x->x.length()));
    }

    /**
     * Prints the longest country name translation together with its language code in the form language=translation.
     */
    public void streamPipeline3() {
        countries.stream().map(country -> country.getTranslations()).flatMap(x->x.entrySet().stream()).filter(x->x.getValue()==(countries.stream().map(Country::getTranslations).flatMap(y->y.values().stream()).max(Comparator.comparingInt(y->y.length())).get())).forEach(System.out::println);
    }

    /**
     * Prints single word country names (i.e., country names that do not contain any space characters).
     */
    public void streamPipeline4() {
        countries.stream()
                .map(country -> country.getName())
                .filter(x->!x.contains(" "))
                .forEach(System.out::println);
    }

    /**
     * Returns the country name with the most number of words.
     */
    public Optional<String> streamPipeline5() {
        return countries.stream()
                .map(country -> country.getName())
                .max(Comparator.comparingInt(x->x.split(" ").length));
    }

    /**
     * Returns whether there exists at least one capital that is a palindrome.
     */
    public boolean streamPipeline6() {
        return countries.stream()
                .map(country -> country.getCapital())
                .anyMatch(x-> new StringBuilder(x) == new StringBuilder(x).reverse());
    }

    /**
     * Returns the country name with the most number of {@code 'e'} characters ignoring case.
     */
    public Optional<String> streamPipeline7() {
        // TODO
        return null;
    }

    /**
     *  Returns the capital with the most number of English vowels (i.e., {@code 'a'}, {@code 'e'}, {@code 'i'}, {@code 'o'}, {@code 'u'}).
     */
    public Optional<String> streamPipeline8() {
        // TODO
        return null;
    }

    /**
     * Returns a map that contains for each character the number of occurrences in country names ignoring case.
     */
    public Map<Character, Long> streamPipeline9() {
        return countries.stream()
                .flatMap(country -> country.getName().toLowerCase().chars().mapToObj(c->(char) c))
                .collect(Collectors.groupingBy(c->c,Collectors.counting()));
    }

    /**
     * Returns a map that contains the number of countries for each possible timezone.
     */
    public Map<ZoneId, Long> streamPipeline10() {
        return countries.stream()
                .flatMap(country -> country.getTimezones().stream())
                .collect(Collectors.groupingBy(c->c,Collectors.counting()));
    }

    /**
     * Returns the number of country names by region that starts with their two-letter country code ignoring case.
     */
    public Map<Region, Long> streamPipeline11() {
        return countries.stream()
                .filter(country -> country.getName().toUpperCase().startsWith(country.getCode()))
                .collect(Collectors.groupingBy(Country::getRegion,Collectors.counting()));
    }

    /**
     * Returns a map that contains the number of countries whose population is greater or equal than the population average versus the the number of number of countries with population below the average.
     */
    public Map<Boolean, Long> streamPipeline12() {
        return countries.stream()
                .map(Country::getPopulation)
                .collect(Collectors.groupingBy(x-> x>=countries.stream().mapToLong(country->country.getPopulation()).summaryStatistics().getAverage(),Collectors.counting()));
    }

    /**
     * Returns a map that contains for each country code the name of the corresponding country in Portuguese ({@code "pt"}).
     */
    public Map<String, String> streamPipeline13() {
        return countries.stream()
                .collect(Collectors.toMap(Country::getCode,country ->country.getTranslations().get("pt")));
    }

    /**
     * Returns the list of capitals by region whose name is the same is the same as the name of their country.
     */
    public Map<Region, List<String>> streamPipeline14() {
        return countries.stream()
                .collect(Collectors.groupingBy(Country::getRegion,Collectors.filtering(country -> country.getName().equals(country.getCapital()),Collectors.mapping(Country::getCapital,Collectors.toList()))));
    }

    /**
     *  Returns a map of country name-population density pairs.
     */
    public Map<Region, Double> streamPipeline15() {
        // TODO
        return null;
    }

}
