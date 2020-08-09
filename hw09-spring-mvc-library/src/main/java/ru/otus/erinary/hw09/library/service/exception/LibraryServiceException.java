package ru.otus.erinary.hw09.library.service.exception;

/**
 * Исключения при работе с DAO.
 */
public class LibraryServiceException extends RuntimeException {

    public LibraryServiceException(String message) {
        super(message);
    }

}
