package ru.otus.erinary.studentapp.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.erinary.studentapp.model.Exercise;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileExerciseLoaderTest {

    private FileExerciseLoader loader;

    @BeforeEach
    void setup() {
        loader = new FileExerciseLoader("test-exercise.csv");
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