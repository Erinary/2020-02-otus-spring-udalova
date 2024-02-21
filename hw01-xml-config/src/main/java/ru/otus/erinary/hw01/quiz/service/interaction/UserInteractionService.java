package ru.otus.erinary.hw01.quiz.service.interaction;

import ru.otus.erinary.hw01.quiz.model.User;

/**
 * Interface used to interact with the user and user's input.
 */
public interface UserInteractionService {

    /**
     * Reads user's command.
     *
     * @return user's command
     */
    String readCommand();

    /**
     * Creates a new {@link User} instance based on user's input.
     *
     * @return {@link User}
     */
    User getUser();

}
