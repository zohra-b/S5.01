package cat.itacademy.s5._1.validations;

import cat.itacademy.s5._1.exceptions.EmptyInputException;
import cat.itacademy.s5._1.exceptions.ValueOutOfRangeException;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ValidateInputs {

    private static final Scanner SC = new Scanner(System.in);

    public static void validateFieldNotEmpty(String input) throws EmptyInputException {
        if (input.isEmpty()) {
            throw new EmptyInputException("the field cannot be empty");
        }
    }

    public static String readInput() {
        return SC.nextLine().trim();
    }

    public static String checkString() throws EmptyInputException {
        String inputStr = readInput();
        if (inputStr.isEmpty()) {
            throw new EmptyInputException("the field cannot be empty");
        } else {
            return inputStr;
        }
    }

    public static String validateString(String message) {
        while (true) {
            try {
                System.out.print(message);
                return checkString();
            } catch (EmptyInputException | NoSuchElementException | IllegalStateException e) {
                System.out.println("Error, " + e.getMessage());
            }
        }
    }

    public static void checkRangeNumber(String input, int minimum, int maximum) throws ValueOutOfRangeException {
        int number = Integer.parseInt(input);
        if (number < minimum || number > maximum) {
            throw new ValueOutOfRangeException(
                    "The value entered must be between " + minimum + " and " + maximum + ". "
            );
        }
    }

    public static int validateIntegerBetweenOnRange(String message, int minimum, int maximum) {
        while (true) {
            try {
                System.out.print(message);
                String input = readInput();
                validateFieldNotEmpty(input);
                checkRangeNumber(input, minimum, maximum);
                return Integer.parseInt(input);
            } catch (NullPointerException | NumberFormatException e) {
                System.out.println("Format error");
            } catch (EmptyInputException | NoSuchElementException | IllegalStateException |
                     ValueOutOfRangeException e) {
                System.out.println("Error, " + e.getMessage());
            }
        }
    }

    public static <T extends Enum<T>> T validateEnum(Class<T> enumClass, String message) {
        while (true) {
            try {
                System.out.print(message);
                String input = readInput().toUpperCase();
                return Enum.valueOf(enumClass, input);
            } catch (IllegalArgumentException | NoSuchElementException | IllegalStateException e) {
                System.out.println("Error, please enter a valid value: " + Arrays.toString(enumClass.getEnumConstants()));
            }
        }
    }

    public static String validateEmail(String message) {
        while (true) {
            try {
                System.out.print(message);
                String email = checkString();
                isValidEmail(email);

                return email;
            } catch (EmptyInputException | NoSuchElementException | IllegalStateException |
                     IllegalArgumentException e) {
                System.out.println("Error, " + e.getMessage());
            }
        }
    }

    public static void isValidEmail(String email) {
        String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        if (!email.matches(regex)) {
            throw new IllegalArgumentException("Invalid email format.");
        }
    }

}
