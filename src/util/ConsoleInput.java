package util;

import validator.InputValidator;
import exception.InvalidInputException;
import java.util.Scanner;

public class ConsoleInput {
    private static final Scanner scanner = new Scanner(System.in);

    public static InputData readInput() {
        System.out.println("\n=== Ввод данных для обработки ===");

        String text = readText();
        int copies = readCopies();
        int lengthLimit = readLengthLimit();

        try {
            InputValidator.validateInput(text, copies, lengthLimit);
        } catch (InvalidInputException e) {
            System.out.println("Ошибка валидации: " + e.getMessage());
            return readInput();
        }

        return new InputData(text, copies, lengthLimit);
    }

    private static String readText() {
        while (true) {
            System.out.print("Введите текст для обработки: ");
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                try {
                    InputValidator.validateText(input);
                    return input;
                } catch (InvalidInputException e) {
                    System.out.println("Ошибка: " + e.getMessage());
                }
            } else {
                System.out.println("Ошибка: текст не может быть пустым.");
            }
        }
    }

    private static int readCopies() {
        while (true) {
            System.out.print("Введите количество копий (N): ");
            String input = scanner.nextLine().trim();

            try {
                int copies = Integer.parseInt(input);
                InputValidator.validateCopies(copies);
                return copies;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число.");
            } catch (InvalidInputException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private static int readLengthLimit() {
        while (true) {
            System.out.print("Введите ограничение длины (L): ");
            String input = scanner.nextLine().trim();

            try {
                int limit = Integer.parseInt(input);
                InputValidator.validateLengthLimit(limit);
                return limit;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число.");
            } catch (InvalidInputException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    public static boolean askToContinue() {
        System.out.print("\nХотите продолжить? (да/нет): ");
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("да") || response.equals("д") || response.equals("yes") || response.equals("y");
    }

    public static void close() {
        scanner.close();
    }

    public static class InputData {
        private final String text;
        private final int copies;
        private final int lengthLimit;

        public InputData(String text, int copies, int lengthLimit) {
            this.text = text;
            this.copies = copies;
            this.lengthLimit = lengthLimit;
        }

        public String getText() {
            return text;
        }

        public int getCopies() {
            return copies;
        }

        public int getLengthLimit() {
            return lengthLimit;
        }

        @Override
        public String toString() {
            return String.format("text='%s', copies=%d, lengthLimit=%d", text, copies, lengthLimit);
        }
    }
}