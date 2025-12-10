package service;

import entity.WordFunction;
import validator.InputValidator;
import exception.InvalidInputException;
import exception.WordProcessingException;
import java.util.function.BiFunction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WordService {

    private static final Logger logger = LogManager.getLogger(WordService.class);

    public static WordFunction copier = (str, N, L) -> {
        try {
            logger.debug("Начало обработки: строка='{}', N={}, L={}", str, N, L);

            InputValidator.validateInput(str, N, L);

            String trimmed = str.trim();
            logger.debug("Обрезанная строка: '{}'", trimmed);

            StringBuilder fullResult = new StringBuilder();
            for (int i = 0; i < N; i++) {
                if (i > 0) {
                    fullResult.append(", ");
                }
                fullResult.append(trimmed);
            }

            String fullString = fullResult.toString();
            logger.debug("Полная строка с {} копиями: '{}' (длина: {})", N, fullString, fullString.length());

            if (fullString.length() <= L) {
                logger.info("Результат умещается в лимит L={}, возвращаем полную строку", L);
                return fullString;
            }

            int lastComma = fullString.lastIndexOf(',', L);
            logger.debug("Последняя запятая до позиции {}: {}", L, lastComma);

            String result;
            if (lastComma > 0) {
                result = fullString.substring(0, lastComma);
                logger.info("Обрезано по последней запятой: '{}' (длина: {})", result, result.length());
            } else {
                int lastSpace = fullString.lastIndexOf(' ', L);
                logger.debug("Последний пробел до позиции {}: {}", L, lastSpace);

                if (lastSpace > 0) {
                    result = fullString.substring(0, lastSpace);
                    logger.info("Обрезано по последнему пробелу: '{}' (длина: {})", result, result.length());
                } else {
                    result = fullString.substring(0, L);
                    logger.warn("Обрезано по лимиту L (возможно обрезание слова): '{}' (длина: {})", result, result.length());
                }
            }

            return result;

        } catch (InvalidInputException e) {
            logger.error("Ошибка валидации входных данных: {}", e.getMessage());
            throw new WordProcessingException("Неверные входные данные: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Неожиданная ошибка при обработке: {}", e.getMessage(), e);
            throw new WordProcessingException("Ошибка обработки: " + e.getMessage(), e);
        }
    };

    public static BiFunction<String, int[], String> builtInCopier = (str, params) -> {
        try {
            if (params == null || params.length != 2) {
                throw new WordProcessingException("Параметры должны быть массивом из двух элементов [N, L]");
            }

            int N = params[0];
            int L = params[1];

            logger.debug("Встроенный копировщик: строка='{}', N={}, L={}", str, N, L);

            String result = copier.process(str, N, L);
            logger.info("Встроенный копировщик вернул: '{}'", result);

            return result;

        } catch (WordProcessingException e) {
            logger.error("Ошибка в builtInCopier: {}", e.getMessage());
            throw e;
        }
    };
}