package ru.otus.erinary.hw02.quiz.dao;

import ru.otus.erinary.hw02.quiz.model.Exercise;

import java.util.List;

/**
 * Интерфейс сервиса для загрузки упражнений.
 */
public interface ExerciseLoader {

    /**
     * Возвращает список упражнений.
     *
     * @return список упражнений {@link Exercise}
     */
    List<Exercise> getExercises();

}
