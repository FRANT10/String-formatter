package run;

import service.WordService;
import util.ConsoleInput;
import util.ConsoleInput.InputData;
import exception.WordProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApplicationRunner {
    private static final Logger logger = LogManager.getLogger(ApplicationRunner.class);

    public void run() {
        logger.info("Запуск приложения Word Processor");
        showWelcomeMessage();

        try {
            boolean continueProcessing;
            do {
                processInput();
                continueProcessing = ConsoleInput.askToContinue();
            } while (continueProcessing);
        } finally {
            showGoodbyeMessage();
            ConsoleInput.close();
        }
    }

    private void showWelcomeMessage() {
        System.out.println("""
            =========================================
                Word Processor - Обработчик текста
            =========================================
            Это приложение создает N копий текста,
            разделяет их запятыми и обрезает
            до указанной длины L.
            =========================================""");
    }

    private void showGoodbyeMessage() {
        System.out.println("""
            
            =========================================
                Спасибо за использование приложения!
            =========================================""");
    }

    private void processInput() {
        try {
            InputData input = ConsoleInput.readInput();
            processWithCustomInterface(input);
            processWithBuiltInFunction(input);
        } catch (Exception e) {
            handleError("Ошибка при обработке", e);
        }
    }

    private void processWithCustomInterface(InputData input) {
        processWithMethod(input, "собственный интерфейс",
                () -> WordService.copier.process(input.getText(), input.getCopies(), input.getLengthLimit()));
    }

    private void processWithBuiltInFunction(InputData input) {
        processWithMethod(input, "встроенный BiFunction",
                () -> WordService.builtInCopier.apply(input.getText(),
                        new int[]{input.getCopies(), input.getLengthLimit()}));
    }

    private void processWithMethod(InputData input, String methodName, Processor processor) {
        System.out.println("\n--- Способ (" + methodName + ") ---");

        try {
            String result = processor.process();
            displayResult(input, result, methodName);
            logger.info("Успешная обработка {}: {} -> '{}'", methodName, input, result);
        } catch (WordProcessingException e) {
            handleError("Ошибка обработки " + methodName, e);
        }
    }

    private void displayResult(InputData input, String result, String method) {
        String fullString = createFullString(input.getText(), input.getCopies());

        System.out.println("""
            Обработка завершена успешно!
            Входные данные:
              Текст: '%s'
              Копий: %d
              Лимит: %d
            Результат (%s):
              '%s'
              Длина: %d символов%s"""
                .formatted(
                        input.getText(), input.getCopies(), input.getLengthLimit(),
                        method, result, result.length(),
                        fullString.length() > input.getLengthLimit() ?
                                "\n  (обрезка: было " + fullString.length() + " символов)" : ""
                ));
    }

    private String createFullString(String text, int copies) {
        String trimmed = text.trim();
        return (trimmed + ", ").repeat(copies - 1) + trimmed;
    }

    private void handleError(String message, Exception e) {
        System.out.println(message + ": " + e.getMessage());
        logger.error("{}: {}", message, e.getMessage());
    }

    @FunctionalInterface
    private interface Processor {
        String process() throws WordProcessingException;
    }
}