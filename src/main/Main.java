package main;

/**
 * Вариант задания:
 * С помощью лямбда-выражений создать метод, который на вход принимает строку,
 * количество копий N, ограничение на общую длину символов L.
 * Поставить запятые после каждого слова, сделать N копий, и если слов больше L —
 * не выводить остальные слова.
 */
import run.ApplicationRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Инициализация приложения Word Processor");

        try {
            ApplicationRunner runner = new ApplicationRunner();
            runner.run();
        } catch (Exception e) {
            logger.fatal("Критическая ошибка при запуске приложения: {}", e.getMessage(), e);
            System.err.println("Критическая ошибка: " + e.getMessage());
            System.exit(1);
        }
    }
}