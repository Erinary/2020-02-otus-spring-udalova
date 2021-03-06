package ru.otus.erinary.hw02.quiz.service.exercise;

import ru.otus.erinary.hw02.quiz.model.Exercise;

import java.util.List;

/**
 * Интерфейс сервиса для получения и работы с упражнениями
 */
public interface ExerciseService {

    List<Exercise> getExercises();

    boolean checkAnswer(Exercise exercise, String answer);
}
