package ru.otus.erinary.studentapp.service;

import org.springframework.stereotype.Service;
import ru.otus.erinary.studentapp.dao.ExerciseLoader;
import ru.otus.erinary.studentapp.model.Exercise;

import java.util.List;

/**
 * Сервис для получения и работы с упражнениями
 */
@Service
public class ExerciseService {

    private final ExerciseLoader loader;

    public ExerciseService(ExerciseLoader loader) {
        this.loader = loader;
    }

    public List<Exercise> getExercises() {
        return loader.getExercises();
    }

    public boolean checkAnswer(Exercise exercise, String answer) {
        return exercise.getAnswer().equals(answer);
    }
}
