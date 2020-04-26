package ru.otus.erinary.hw01.quiz.dao;

import ru.otus.erinary.hw01.quiz.model.Exercise;

import java.util.List;

/**
 * Интерфейс для загрузки упражнений
 */
public interface ExerciseLoader {

    List<Exercise> getExercises();

}
