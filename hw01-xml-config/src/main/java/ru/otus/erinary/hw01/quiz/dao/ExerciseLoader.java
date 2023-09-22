package ru.otus.erinary.hw01.quiz.dao;

import ru.otus.erinary.hw01.quiz.model.Exercise;

import java.util.List;

/**
 * Интерфейс для загрузки упражнений.
 */
public interface ExerciseLoader {

    /**
     * Возвращает список упражнений.
     *
     * @return список упражнений {@link Exercise}
     */
    List<Exercise> getExercises();

}
