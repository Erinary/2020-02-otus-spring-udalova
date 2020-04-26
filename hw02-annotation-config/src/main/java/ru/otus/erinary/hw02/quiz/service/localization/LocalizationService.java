package ru.otus.erinary.hw02.quiz.service.localization;

/**
 * Интерфейс сервиса для локализации сообщений
 */
public interface LocalizationService {

    String localizeMessage(String code);

    String getLocaleCode();
}
