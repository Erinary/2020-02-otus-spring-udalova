package ru.otus.erinary.hw02.quiz.service.localization;

/**
 * Interface used to localize messages.
 */
public interface LocalizationService {

    /**
     * Returns a localized message by the corresponding message code.
     *
     * @param code the message code
     * @return localized message
     */
    String localizeMessage(String code);

    /**
     * the language code of current locale.
     *
     * @return language code
     */
    String getLocaleCode();
}
