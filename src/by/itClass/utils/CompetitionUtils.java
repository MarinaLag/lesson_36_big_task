package by.itClass.utils;

import by.itClass.exceptions.CompetitionException;
import by.itClass.model.Animal;
import by.itClass.model.Cat;
import by.itClass.model.Dog;
import lombok.experimental.UtilityClass;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static by.itClass.utils.AnimalFactory.EMAIL_REGEX;

@UtilityClass
public class CompetitionUtils {
    private static final String PATH_TO_FILE = "src/by/itClass/resources/animals.txt";

    public static void parseFile(List<Cat> cats, List<Dog> dogs, Map<String, String> errors) {
        try (Scanner scanner = new Scanner(new FileReader(PATH_TO_FILE))) {
            while (scanner.hasNextLine()) {
                // System.out.println(scanner.nextLine());
                fillingCollections(scanner.nextLine(), cats, dogs, errors);
            }
        } catch (FileNotFoundException exception) {
            System.out.printf("File has not found by path %s %n", PATH_TO_FILE);
            System.exit(1);
        }
    }

    private static void fillingCollections(String textLine, List<Cat> cats, List<Dog> dogs, Map<String, String> errors) {
        try {
            Animal animal = AnimalFactory.getInstance(textLine);
            if (animal instanceof Cat) {
                cats.add((Cat) animal);
            } else {
                dogs.add((Dog) animal);
            }
        } catch (CompetitionException exception) {
            processException(errors, exception);
        }
    }

    private static void processException(Map<String, String> errors, CompetitionException exception) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(exception.getErrorLine());
        String str = "";
        while (matcher.find()) {
            str = matcher.group();
        }
        if (!str.isEmpty()) {
            errors.put(str, String.format("Error in string %s - %s", exception.getErrorLine(), exception.getCause(), exception.getMessage()));
        }
    }

    public static <T> List<T> sortByBirthDate(List<T> animals) {
        return animals.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public static void printResult(List<Cat> cats, List<Dog> dogs, Map<String, String> errors) {
        System.out.println("Cat list size = " + cats.size());
        printList(cats);
        System.out.println("Dogs list size = " + dogs.size());
        printList(dogs);
        System.out.println("Errors quantity = " + errors.size());
        printMap(errors);
    }

    private static <T> void printList(List<T> collection) {
        collection.forEach(System.out::println);
    }

    private static void printMap(Map<String, String> errors) {
        errors.forEach((key, value) -> System.out.println(key + "; " + value));
    }

    private static final LocalDate ageDeLimiter = LocalDate.now().minusYears(2);
    private static final Predicate<Animal> YOUNG_PREDICATE = it -> it.getBirthDate().isAfter(ageDeLimiter);//после - молодой
    private static final Predicate<Animal> OLD_PREDICATE = it -> it.getBirthDate().isBefore(ageDeLimiter);//до- старый

    public static <T extends Animal> List<T> filterAnimals(List<T> animals, boolean isYoung) {
        return animals.stream()
                .filter(isYoung ? YOUNG_PREDICATE : OLD_PREDICATE)
                .collect(Collectors.toList());
    }

    public static void printResult(List<Cat> youngCats, List<Dog> youngDogs, List<Cat> oldCats, List<Dog> oldDogs, Map<String, String> errors) {
        System.out.println("First day participants: ");
        System.out.println("Cat list size = " + youngCats.size());
        printList(youngCats);
        System.out.println("Dogs list size = " + youngDogs.size());
        printList(youngDogs);

        System.out.println("Second day participants: ");
        System.out.println("Cat list size = " + oldCats.size());
        printList(oldCats);
        System.out.println("Dogs list size = " + oldDogs.size());
        printList(oldDogs);

        System.out.println("Errors quantity = " + errors.size());
        printMap(errors);
    }


}
