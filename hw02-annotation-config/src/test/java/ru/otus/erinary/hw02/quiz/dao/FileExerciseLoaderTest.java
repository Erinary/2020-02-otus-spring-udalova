package ru.otus.erinary.hw02.quiz.dao;

import org.junit.jupiter.api.Test;
import ru.otus.erinary.hw02.quiz.model.Exercise;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileExerciseLoaderTest {

    private static final String BASE_FILE_NAME = "test-exercise.csv";
    private static final String BAD_FILE_NAME = "bad-test-exercise.csv";
    private static final String LOCALE_CODE = "en";
    public static final int EXPECTED_EXERCISE_NUMBER = 3;
    public static final String QUESTION = "question";
    public static final String ANSWER = "answer";
    public static final String RESPONSE = "1) A";

    private FileExerciseLoader loader;

    @Test
    void getExercises() {
        loader = new FileExerciseLoader(LOCALE_CODE, BASE_FILE_NAME);
        List<Exercise> exercises = loader.getExercises();
        assertEquals(EXPECTED_EXERCISE_NUMBER, exercises.size());
        exercises.forEach(exercise ->
                assertEquals(EXPECTED_EXERCISE_NUMBER, exercise.getResponses().size())
        );

        Exercise testExercise = exercises.get(0);
        assertEquals(QUESTION, testExercise.getQuestion());
        assertEquals(ANSWER, testExercise.getAnswer());
        assertEquals(RESPONSE, testExercise.getResponses().get(0));
    }

    @Test
    void getExerciseFromBadFile() {
        loader = new FileExerciseLoader(LOCALE_CODE, BAD_FILE_NAME);
        assertThrows(ExerciseLoaderException.class, () -> loader.getExercises());
    }
}