package ru.otus.erinary.hw02.quiz.dao;

/**
 * Исключения при загрузке упражнений.
 */
public class ExerciseLoaderException extends RuntimeException {

    /**
     * Создает новый экземпляр {@link ExerciseLoaderException}.
     *
     * @param message текст ошибки
     */
    public ExerciseLoaderException(final String message) {
        super(message);
    }

    /**
     * Создает новый экземпляр {@link ExerciseLoaderException}.
     *
     * @param message текст ошибки
     * @param cause   причина ошибки
     */
    public ExerciseLoaderException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
