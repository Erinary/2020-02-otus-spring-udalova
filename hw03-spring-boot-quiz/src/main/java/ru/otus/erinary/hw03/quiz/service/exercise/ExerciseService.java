package ru.otus.erinary.hw03.quiz.service.exercise;


import ru.otus.erinary.hw03.quiz.model.Exercise;

import java.util.List;

/**
 * Интерфейс сервиса для получения и работы с упражнениями
 */
public interface ExerciseService {

    List<Exercise> getExercises();

    boolean checkAnswer(final Exercise exercise, final String answer);
}
