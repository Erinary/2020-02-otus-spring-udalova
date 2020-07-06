package ru.otus.erinary.hw06.hibernatelibrary.dao.exception;

/**
 * Исключения при работе с DAO.
 */
public class DaoException extends RuntimeException {

    public DaoException(String message) {
        super(message);
    }

}
