package ru.otus.erinary.hw04.quiz.service;

/**
 * Interface that represents the quiz.
 */
public interface QuizService {

    /**
     * Calls the help information.
     */
    void help();

    /**
     * Runs the quiz.
     */
    void quiz();

    /**
     * Quits the quiz.
     */
    void quit();

    /**
     * Creates a new user.
     *
     * @param name    user's name
     * @param surname user's surname
     */
    void createUser(String name, String surname);

    /**
     * Checks if user exists.
     *
     * @return message code with the check's result
     */
    boolean checkIfUserExists();
}
