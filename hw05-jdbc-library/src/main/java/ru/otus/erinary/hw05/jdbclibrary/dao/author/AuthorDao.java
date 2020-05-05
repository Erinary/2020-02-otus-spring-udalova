package ru.otus.erinary.hw05.jdbclibrary.dao.author;

import ru.otus.erinary.hw05.jdbclibrary.model.Author;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс репозитория для {@link Author}
 */
public interface AuthorDao {

    long save(Author author);

    Optional<Author> findById(long id);

    List<Author> findAll();

    void delete(long id);
}
