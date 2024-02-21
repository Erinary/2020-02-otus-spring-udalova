package ru.otus.erinary.hw02.quiz.service.interaction.output;

/**
 * Interface used to send output to user.
 */
public interface OutputInteractionService {

    /**
     * Sends the message to the user.
     *
     * @param message message
     */
    void sendMessage(String message);

}
