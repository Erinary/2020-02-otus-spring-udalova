package ru.otus.erinary.studentapp.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.erinary.studentapp.model.Exercise;
import ru.otus.erinary.studentapp.service.LocalizationService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileExerciseLoaderTest {

    private static final String BASE_FILE_NAME = "test-exercise.csv";
    private static final String LOCALE_CODE = "en";
    private FileExerciseLoader loader;

    @BeforeEach
    void setup() {
        LocalizationService service = Mockito.mock(LocalizationService.class);
        Mockito.when(service.getLocaleCode()).thenReturn(LOCALE_CODE);
        loader = new FileExerciseLoader(BASE_FILE_NAME, service);
    }

    @Test
    void getExercises() {
        List<Exercise> exercises = loader.getExercises();
        assertEquals(3, exercises.size());
        exercises.forEach(exercise ->
                assertEquals(3, exercise.getResponses().size())
        );

        Exercise testExercise = exercises.get(0);
        assertEquals("question", testExercise.getQuestion());
        assertEquals("answer", testExercise.getAnswer());
        assertEquals("1) A", testExercise.getResponses().get(0));
    }
}