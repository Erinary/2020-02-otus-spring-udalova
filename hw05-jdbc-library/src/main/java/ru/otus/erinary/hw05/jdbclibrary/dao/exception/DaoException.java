package ru.otus.erinary.hw05.jdbclibrary.dao.exception;

/**
 * An exception when working with DAO.
 */
public class DaoException extends RuntimeException {

    /**
     * Constructs a new exception with the specified message.
     *
     * @param message detailed message
     */
    public DaoException(final String message) {
        super(message);
    }

}
