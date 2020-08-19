package ru.otus.erinary.hw08.library.dao.exception;

/**
 * Исключения при работе с DAO.
 */
public class DaoException extends RuntimeException {

    public DaoException(String message) {
        super(message);
    }

}
