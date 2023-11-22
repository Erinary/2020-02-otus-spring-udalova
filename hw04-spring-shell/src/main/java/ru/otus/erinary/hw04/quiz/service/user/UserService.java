package ru.otus.erinary.hw04.quiz.service.user;

import ru.otus.erinary.hw04.quiz.model.User;

/**
 * Interface used to work with current user.
 */
public interface UserService {

    /**
     * Creates current user.
     *
     * @param name    user's name
     * @param surname user's surname
     */
    void createCurrentUser(String name, String surname);

    /**
     * Returns current user.
     *
     * @return current user
     */
    User getCurrentUser();

    /**
     * Check if user is created.
     *
     * @return true if user is created.
     */
    boolean isUserCreated();
}
