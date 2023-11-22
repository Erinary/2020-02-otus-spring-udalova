package ru.otus.erinary.hw04.quiz.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * A user.
 */
@Data
@RequiredArgsConstructor
public class User {

    private final String name;

    private final String surname;

    private int correctAnswersCounter = 0;

    /**
     * Increases the user's correct response counter.
     */
    public void raiseAnswersCounter() {
        correctAnswersCounter++;
    }

}
