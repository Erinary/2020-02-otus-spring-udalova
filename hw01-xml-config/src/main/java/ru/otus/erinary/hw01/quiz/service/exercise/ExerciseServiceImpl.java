package ru.otus.erinary.hw01.quiz.service.exercise;

import ru.otus.erinary.hw01.quiz.dao.ExerciseLoader;
import ru.otus.erinary.hw01.quiz.model.Exercise;

import java.util.List;

/**
 * Realization of {@link ExerciseService}.
 */
public final class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseLoader loader;

    /**
     * Creates a new {@link ExerciseServiceImpl} instance.
     *
     * @param loader {@link ExerciseLoader}
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
