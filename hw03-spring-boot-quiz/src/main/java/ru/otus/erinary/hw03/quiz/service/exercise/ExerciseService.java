package ru.otus.erinary.hw03.quiz.service.exercise;


import ru.otus.erinary.hw03.quiz.model.Exercise;

import java.util.List;

/**
 * Interface used for acquiring and working with exercises.
 */
public interface ExerciseService {

    /**
     * Returns list of exercises.
     *
     * @return list of exercises {@link Exercise}
     */
    List<Exercise> getExercises();

    /**
     * Check if the given answer is correct.
     *
     * @param exercise exercise
     * @param answer   users answer
     * @return true, if the users answer is correct
     */
    boolean checkAnswer(Exercise exercise, String answer);
}
