package ru.otus.erinary.hw02.quiz.dao;

import ru.otus.erinary.hw02.quiz.model.Exercise;

import java.util.List;

/**
 * Interface used for exercise loading.
 */
public interface ExerciseLoader {

    /**
     * Returns list of exercises.
     *
     * @return list of exercises {@link Exercise}
     */
    List<Exercise> getExercises();

}
