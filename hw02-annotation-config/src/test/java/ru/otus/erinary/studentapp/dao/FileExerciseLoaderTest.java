package ru.otus.erinary.studentapp.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.erinary.studentapp.model.Exercise;
import ru.otus.erinary.studentapp.service.localization.LocalizationServiceImpl;

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
    private LocalizationServiceImpl service;

    @BeforeEach
    void setup() {
        service = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(service.getLocaleCode()).thenReturn(LOCALE_CODE);
    }

    @Test
    void getExercises() {
        loader = new FileExerciseLoader(BASE_FILE_NAME, service);
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
        loader = new FileExerciseLoader(BAD_FILE_NAME, service);
        assertThrows(ExerciseLoaderException.class, () -> loader.getExercises());
    }
}