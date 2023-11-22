package ru.otus.erinary.hw04.quiz.service.localization;

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
     * Returns a localized message with parameters by the corresponding message code.
     *
     * @param code   the message code
     * @param params message parameters
     * @return localized message
     */
    String localizeMessageWithParams(String code, String[] params);

}
