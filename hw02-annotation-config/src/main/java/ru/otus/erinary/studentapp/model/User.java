package ru.otus.erinary.studentapp.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Модель пользователя
 */
@Data
@RequiredArgsConstructor
public class User {

    private final String name;

    private final String surname;

    private int correctAnswersCounter = 0;

    public void raiseAnswersCounter() {
        correctAnswersCounter++;
    }

}
