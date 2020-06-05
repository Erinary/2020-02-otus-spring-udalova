package ru.otus.erinary.hw05.jdbclibrary.dao.book;

import ru.otus.erinary.hw05.jdbclibrary.model.Book;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс репозитория для {@link Book}
 */
public interface BookDao {

    Book save(Book book);

    Optional<Book> findById(Long id);

    List<Book> findAll();

    List<Book> findAllByAuthorId(Long authorId);

    List<Book> findAllByGenreId(Long genreId);

    void delete(Long id);
}
