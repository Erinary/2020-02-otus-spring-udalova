package ru.otus.erinary.hw06.hibernatelibrary.dao.book;

import ru.otus.erinary.hw06.hibernatelibrary.model.Book;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс репозитория для {@link Book}
 */
public interface BookRepository {

    Book save(Book book);

    Optional<Book> findById(Long id);

    List<Book> findAll();

    List<Book> findAllByAuthorId(Long authorId);

    List<Book> findAllByGenreId(Long genreId);

    void delete(Long id);
}
