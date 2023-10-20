package by.itClass.utils;

import by.itClass.exceptions.CompetitionException;
import by.itClass.model.Animal;
import by.itClass.model.Cat;
import by.itClass.model.Dog;
import by.itClass.model.Genus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AnimalFactory {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-y");
    private static final String EMAIL_REGEX = "[\\w!#$%&*+/=?^'`{|}~\\-]+(?:\\.[\\w!#$%&*+/=?^'`{|}~\\-]+)*@(?:[a-zA-Z\\d](?:[a-zA-Z\\d\\-]*[a-zA-Z\\d])?\\.)+[a-zA-Z\\d][a-zA-Z\\d\\-]*[a-zA-Z\\d]";

    public static Animal getInstance(String line) throws CompetitionException {
        String[] stringsArray = line.split("[;,]");
        try {
            long chipNumber = Long.parseLong(validateStringMatches(stringsArray[0], "(?=\\d{15}\\b)\\d{3}09(?:81|56)\\d{8}"));
            String name = validateStringByEmpty(stringsArray[2]);
            LocalDate birthDate = LocalDate.parse(stringsArray[3], FORMATTER);
            String breed = validateStringByEmpty(stringsArray[4]);
            String email = validateStringMatches(stringsArray[5], EMAIL_REGEX);
            return "cat".equalsIgnoreCase(stringsArray[1])
                    ? new Cat(chipNumber, Genus.of(stringsArray[1]),name,birthDate,breed,email)
                    :new Dog(chipNumber,Genus.DOG,name,birthDate,breed,email);
        }catch (IllegalStateException exception){
            throw new CompetitionException(exception,line);
        }
    }

    private static String validateStringMatches(String value, String regEx) {
        if (value.matches(regEx)) {
            return value;
        }
        throw new IllegalStateException("Chip Number or Email not valid");
    }

    private static String validateStringByEmpty(String value) {
        if (!value.isEmpty()) {
            return value;
        }
        throw new IllegalStateException("Name or Bread is value");
    }
}
