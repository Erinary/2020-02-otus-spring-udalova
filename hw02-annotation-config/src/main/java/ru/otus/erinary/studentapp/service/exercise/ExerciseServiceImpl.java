package ru.otus.erinary.studentapp.service.exercise;

import org.springframework.stereotype.Service;
import ru.otus.erinary.studentapp.dao.ExerciseLoader;
import ru.otus.erinary.studentapp.model.Exercise;

import java.util.List;

/**
 * Сервис для получения и работы с упражнениями
 */
@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseLoader loader;

    public ExerciseServiceImpl(ExerciseLoader loader) {
        this.loader = loader;
    }

    public List<Exercise> getExercises() {
        return loader.getExercises();
    }

    public boolean checkAnswer(Exercise exercise, String answer) {
        return exercise.getAnswer().equals(answer);
    }
}
