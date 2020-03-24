package ru.otus.erinary.hw01.studentapp.dao;

import ru.otus.erinary.hw01.studentapp.model.Exercise;

import java.util.List;

/**
 * Интерфейс для загрузки упражнений
 */
public interface ExerciseLoader {

    List<Exercise> getExercises();

}
