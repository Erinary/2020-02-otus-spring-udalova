package ru.otus.erinary.hw05.jdbclibrary.dao.book;

import ru.otus.erinary.hw05.jdbclibrary.model.Book;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс репозитория для {@link Book}
 */
public interface BookDao {

    long save(Book book);

    Optional<Book> findById(long id);

    List<Book> findAll();

    void delete(long id);
}
