package ru.otus.erinary.hw03.quiz.dao;


import ru.otus.erinary.hw03.quiz.model.Exercise;

import java.util.List;

/**
 * Интерфейс для загрузки упражнений
 */
public interface ExerciseLoader {

    List<Exercise> getExercises();

}
