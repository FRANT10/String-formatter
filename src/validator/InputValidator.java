package validator;

import exception.InvalidInputException;

public class InputValidator {

    public static void validateInput(String text, int copies, int lengthLimit) throws InvalidInputException {
        validateText(text);
        validateCopies(copies);
        validateLengthLimit(lengthLimit);
    }

    public static void validateText(String text) throws InvalidInputException {
        if (text == null) {
            throw new InvalidInputException("Текст не может быть null");
        }

        if (text.trim().isEmpty()) {
            throw new InvalidInputException("Текст не может быть пустым или состоять только из пробелов");
        }

        if (text.length() > 1000) {
            throw new InvalidInputException("Текст слишком длинный (максимум 1000 символов)");
        }

    }

    public static void validateCopies(int copies) throws InvalidInputException {
        if (copies <= 0) {
            throw new InvalidInputException("Количество копий должно быть положительным числом");
        }

        if (copies > 1000) {
            throw new InvalidInputException("Слишком большое количество копий (максимум 1000)");
        }
    }

    public static void validateLengthLimit(int lengthLimit) throws InvalidInputException {
        if (lengthLimit <= 0) {
            throw new InvalidInputException("Ограничение длины должно быть положительным числом");
        }

        if (lengthLimit > 10000) {
            throw new InvalidInputException("Слишком большое ограничение длины (максимум 10000)");
        }
    }
}