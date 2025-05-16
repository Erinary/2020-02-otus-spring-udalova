package ru.otus.erinary.hw07.springdatalibrary.service.exception;

/**
 * An exception thrown during operations in the service layer.
 */
public class LibraryServiceException extends RuntimeException {

    /**
     * Constructs a new exception with the specified message.
     *
     * @param message detailed message
     */
    public LibraryServiceException(final String message) {
        super(message);
    }

}
