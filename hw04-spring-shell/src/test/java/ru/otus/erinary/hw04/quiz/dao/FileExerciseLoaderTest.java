package ru.otus.erinary.hw04.quiz.dao;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.erinary.hw04.quiz.model.Exercise;
import ru.otus.erinary.hw04.quiz.settings.AppSettings;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FileExerciseLoaderTest {

    private static final String BAD_FILE_NAME = "bad-test-exercise.csv";
    public static final int EXPECTED_EXERCISE_NUMBER = 3;
    public static final String QUESTION = "question";
    public static final String ANSWER = "answer";
    public static final String RESPONSE = "1) A";

    @Autowired
    private AppSettings settings;
    private FileExerciseLoader loader;

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
        var spySettings = Mockito.spy(settings);
        Mockito.when(spySettings.getFileName()).thenReturn(BAD_FILE_NAME);

        loader = new FileExerciseLoader(spySettings);
        assertThrows(ExerciseLoaderException.class, () -> loader.getExercises());
    }

}