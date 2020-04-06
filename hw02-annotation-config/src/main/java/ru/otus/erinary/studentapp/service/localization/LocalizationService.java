package ru.otus.erinary.studentapp.service.localization;

/**
 * Интерфейс сервиса для локализации сообщений
 */
public interface LocalizationService {

    String localizeMessage(String code);

    String getLocaleCode();
}
