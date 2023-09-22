package ru.otus.erinary.hw01.quiz.service.exercise;

import ru.otus.erinary.hw01.quiz.model.Exercise;

import java.util.List;

/**
 * Интерфейс сервиса для получения и работы с упражнениями.
 */
public interface ExerciseService {

    /**
     * Возвращает список упражнений.
     *
     * @return список упражнений {@link Exercise}
     */
    List<Exercise> getExercises();

    /**
     * Проверяет корректность переданного ответа.
     *
     * @param exercise упражнение
     * @param answer   ответ пользователя
     * @return true, если ответ пользователя верный
     */
    boolean checkAnswer(Exercise exercise, String answer);
}
