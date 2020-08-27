package ru.otus.erinary.hw08.library.service.exception;

/**
 * Исключения при работе с DAO.
 */
public class LibraryException extends RuntimeException {

    public LibraryException(String message) {
        super(message);
    }

}
