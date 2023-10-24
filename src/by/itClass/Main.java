package by.itClass;

import by.itClass.model.Cat;
import by.itClass.model.Dog;

import static by.itClass.utils.CompetitionUtils.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<Cat> cats = new ArrayList<>();
        List<Dog> dogs = new ArrayList<>();
        Map<String, String> errors = new HashMap<>();

        parseFile(cats, dogs, errors);

        // CompetitionUtils.printResult(cats, dogs, errors);

        // List<Cat> sortedCat = sortByBirthDate(cats); ПРАВИЛЬНО!!!!!!!т.к. вызываем дважды
        // List<Dog> sortedDog = sortByBirthDate(dogs); ПРАВИЛЬНО!!!!!!!т.к. вызываем дважды

        // CompetitionUtils.printResult(sortedCat, sortedDog, errors);

        List<Cat> youngSortedCats = filterAnimals(sortByBirthDate(cats), true);
        List<Cat> oldSortedCats = filterAnimals(sortByBirthDate(cats), false);
        List<Dog> youngSortedDogs = filterAnimals(sortByBirthDate(dogs), true);
        List<Dog> oldSortedDogs = filterAnimals(sortByBirthDate(dogs), false);

        printResult(youngSortedCats, youngSortedDogs, oldSortedCats, oldSortedDogs, errors);

    }
}
