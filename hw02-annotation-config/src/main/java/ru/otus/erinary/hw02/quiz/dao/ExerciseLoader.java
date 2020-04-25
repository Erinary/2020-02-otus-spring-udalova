package ru.otus.erinary.hw02.quiz.dao;

import ru.otus.erinary.hw02.quiz.model.Exercise;

import java.util.List;

/**
 * Интерфейс для загрузки упражнений
 */
public interface ExerciseLoader {

    List<Exercise> getExercises();

}
