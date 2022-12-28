package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Scanner;

public final class InputValidator {
    public static String stringValidator(){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        boolean invalid = true;
        while (invalid) {
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty! Please enter a valid input: ");
                input = scanner.nextLine();
            } else if(input.contains(",")||input.contains(")")||input.contains("(")){
                System.out.println("Your input contains invalid characters! Please enter a valid input with only alphanumerics: ");
                input = scanner.nextLine();
            } else {
                invalid = false;
            }
        }
        return input;
    }

    public static LocalDate dateValidator(){
        Scanner scanner = new Scanner(System.in);
        boolean validDate = false;
        LocalDate dateOfBirth = null;
        while (!validDate){
            try {
                dateOfBirth = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("uuuu-M-d").withResolverStyle(ResolverStyle.STRICT));
                validDate = true;
            } catch (Exception e) {
                System.out.println("Invalid date format!\nPlease try again with yyyy-mm-dd format:");
            }
        }
        return dateOfBirth;
    }

    public static String getValidStringByPrompt(String prompt){
        System.out.println(prompt);
        return stringValidator();
    }

    public static LocalDate getValidDateByPrompt(String prompt){
        System.out.println(prompt);
        return dateValidator();
    }
}
