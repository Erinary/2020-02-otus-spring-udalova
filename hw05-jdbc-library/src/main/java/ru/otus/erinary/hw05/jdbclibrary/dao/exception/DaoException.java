package ru.otus.erinary.hw05.jdbclibrary.dao.exception;

/**
 * Исключения при работе с DAO.
 */
public class DaoException extends RuntimeException {

    public DaoException(String message) {
        super(message);
    }

}
