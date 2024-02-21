package ru.otus.erinary.hw02.quiz.service.interaction.input;

import ru.otus.erinary.hw02.quiz.model.User;

/**
 * Interface used to process user's input.
 */
public interface InputInteractionService {

    /**
     * Reads user's command.
     *
     * @return command
     */
    String readCommand();

    /**
     * Creates a new {@link User} instance based on user's input.
     *
     * @return {@link User}
     */
    User getUser();

}
