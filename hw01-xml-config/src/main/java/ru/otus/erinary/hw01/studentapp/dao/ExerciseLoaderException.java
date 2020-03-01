package ru.otus.erinary.hw01.studentapp.dao;

/**
 * Исключения при загрузке упражнений
 */
public class ExerciseLoaderException extends RuntimeException {

    public ExerciseLoaderException(String message) {
        super(message);
    }

    public ExerciseLoaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
