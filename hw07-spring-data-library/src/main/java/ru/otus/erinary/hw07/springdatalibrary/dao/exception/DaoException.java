package ru.otus.erinary.hw07.springdatalibrary.dao.exception;

/**
 * Исключения при работе с DAO.
 */
public class DaoException extends RuntimeException {

    public DaoException(String message) {
        super(message);
    }

}
