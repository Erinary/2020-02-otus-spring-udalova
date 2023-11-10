package ru.otus.erinary.hw03.quiz.service.interaction;

/**
 * Interface used to interact with the user.
 */
public interface InteractionService {

    /**
     * Reads user's input.
     *
     * @return input string
     */
    String readLine();

    /**
     * Sends the message to the user.
     *
     * @param message message
     */
    void sendMessage(String message);

    /**
     * Sends localized message by corresponding message code.
     *
     * @param code the message code
     */
    void sendLocalizedMessage(String code);

    /**
     * Sends localized message with parameters by corresponding message code.
     *
     * @param code   the message code
     * @param params message parameters
     */
    void sendLocalizedMessage(String code, String... params);
}
