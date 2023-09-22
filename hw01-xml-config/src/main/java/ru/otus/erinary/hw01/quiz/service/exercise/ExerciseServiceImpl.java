package ru.otus.erinary.hw01.quiz.service.exercise;

import ru.otus.erinary.hw01.quiz.dao.ExerciseLoader;
import ru.otus.erinary.hw01.quiz.model.Exercise;

import java.util.List;

/**
 * Сервис для получения и работы с упражнениями.
 */
public final class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseLoader loader;

    /**
     * Создает новый экземпляр {@link ExerciseServiceImpl}.
     *
     * @param loader сервис для загрузки упражнений
     */
    public ExerciseServiceImpl(final ExerciseLoader loader) {
        this.loader = loader;
    }

    @Override
    public List<Exercise> getExercises() {
        return loader.getExercises();
    }

    @Override
    public boolean checkAnswer(final Exercise exercise, final String answer) {
        return exercise.getAnswer().equals(answer);
    }
}
