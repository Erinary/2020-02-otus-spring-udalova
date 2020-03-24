package ru.otus.erinary.hw01.studentapp.service.exercise;

import ru.otus.erinary.hw01.studentapp.dao.ExerciseLoader;
import ru.otus.erinary.hw01.studentapp.model.Exercise;

import java.util.List;

/**
 * Сервис для получения и работы с упражнениями
 */
public final class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseLoader loader;

    public ExerciseServiceImpl(final ExerciseLoader loader) {
        this.loader = loader;
    }

    public List<Exercise> getExercises() {
        return loader.getExercises();
    }

    public boolean checkAnswer(final Exercise exercise, final String answer) {
        return exercise.getAnswer().equals(answer);
    }
}
