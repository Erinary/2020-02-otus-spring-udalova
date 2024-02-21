package ru.otus.erinary.hw02.quiz.dao;

/**
 *  A {@link ExerciseLoaderException} is thrown when an attempt to load exercises fails.
 */
public class ExerciseLoaderException extends RuntimeException {

    /**
     * Constructs a new exception with the specified message.
     *
     * @param message detailed message
     */
    public ExerciseLoaderException(final String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified message and cause.
     *
     * @param message detailed message
     * @param cause   the cause
     */
    public ExerciseLoaderException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
