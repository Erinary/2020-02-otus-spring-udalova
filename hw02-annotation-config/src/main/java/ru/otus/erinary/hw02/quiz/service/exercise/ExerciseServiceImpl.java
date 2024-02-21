package ru.otus.erinary.hw02.quiz.service.exercise;

import org.springframework.stereotype.Service;
import ru.otus.erinary.hw02.quiz.dao.ExerciseLoader;
import ru.otus.erinary.hw02.quiz.model.Exercise;

import java.util.List;

/**
 * Realization of {@link ExerciseService}.
 */
@Service
public class ExerciseServiceImpl implements ExerciseService {

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
