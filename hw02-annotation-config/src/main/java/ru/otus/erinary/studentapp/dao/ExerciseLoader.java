package ru.otus.erinary.studentapp.dao;

import ru.otus.erinary.studentapp.model.Exercise;

import java.util.List;

/**
 * Интерфейс для загрузки упражнений
 */
public interface ExerciseLoader {

    List<Exercise> getExercises();

}
