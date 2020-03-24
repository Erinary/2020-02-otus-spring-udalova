package ru.otus.erinary.hw01.studentapp.dao;

/**
 * Исключения при загрузке упражнений
 */
public class ExerciseLoaderException extends RuntimeException {

    public ExerciseLoaderException(final String message) {
        super(message);
    }

    public ExerciseLoaderException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
