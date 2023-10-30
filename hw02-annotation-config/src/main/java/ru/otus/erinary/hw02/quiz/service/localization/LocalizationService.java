package ru.otus.erinary.hw02.quiz.service.localization;

/**
 * Интерфейс сервиса для локализации сообщений.
 */
public interface LocalizationService {

    /**
     * Выдает локализированное сообщение, соответствующее переданному коду.
     *
     * @param code код сообщения
     * @return локализированное сообщение
     */
    String localizeMessage(String code);

    /**
     * Возвращает код языка текущей локали.
     *
     * @return код языка
     */
    String getLocaleCode();
}
