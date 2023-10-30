package ru.otus.erinary.hw02.quiz.service.exercise;

import org.springframework.stereotype.Service;
import ru.otus.erinary.hw02.quiz.dao.ExerciseLoader;
import ru.otus.erinary.hw02.quiz.model.Exercise;

import java.util.List;

/**
 * Сервис для получения и работы с упражнениями.
 */
@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseLoader loader;

    /**
     * Создает новый экземпляр {@link ExerciseServiceImpl}.
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
