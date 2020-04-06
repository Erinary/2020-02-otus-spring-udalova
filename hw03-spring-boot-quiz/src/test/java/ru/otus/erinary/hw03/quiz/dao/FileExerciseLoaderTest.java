package ru.otus.erinary.hw03.quiz.dao;

import org.junit.jupiter.api.Test;
import ru.otus.erinary.hw03.quiz.model.Exercise;
import ru.otus.erinary.hw03.quiz.settings.ApplicationSettings;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileExerciseLoaderTest {

    private static final String BASE_FILE_NAME = "test-exercise.csv";
    private static final String BAD_FILE_NAME = "bad-test-exercise.csv";
    private static final String LOCALE_CODE = "en";
    public static final int EXPECTED_EXERCISE_NUMBER = 3;
    public static final String QUESTION = "question";
    public static final String ANSWER = "answer";
    public static final String RESPONSE = "1) A";

    private FileExerciseLoader loader;
    private ApplicationSettings settings;

    @Test
    void getExercises() {
        loader = new FileExerciseLoader(settings);
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
        loader = new FileExerciseLoader(settings);
        assertThrows(ExerciseLoaderException.class, () -> loader.getExercises());
    }

}