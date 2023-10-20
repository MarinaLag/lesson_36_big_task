package by.itClass.utils;

import by.itClass.model.Cat;
import by.itClass.model.Dog;
import lombok.experimental.UtilityClass;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@UtilityClass
public class CompetitionUtils {
    private static final String PATH_TO_FILE = "src/by/itClass/resources/animals.txt";
    public static void parseFile(List<Cat> cats, List<Dog> dogs, Map<String,String> errors){
        try (Scanner scanner = new Scanner(new FileReader(PATH_TO_FILE))){
           while (scanner.hasNextLine()){
               System.out.println(scanner.nextLine());
           }
        } catch (FileNotFoundException exception) {
            System.out.printf("File has not found by path %s %n",PATH_TO_FILE);
            System.exit(1);
        }
    }
}
