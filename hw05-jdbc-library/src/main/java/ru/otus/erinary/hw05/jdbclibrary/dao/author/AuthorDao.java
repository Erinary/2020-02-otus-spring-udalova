package ru.otus.erinary.hw05.jdbclibrary.dao.author;

import ru.otus.erinary.hw05.jdbclibrary.model.Author;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс репозитория для {@link Author}
 */
public interface AuthorDao {

    long insert(Author author);

    void update(Author author);

    Optional<Author> findById(long id);

    Optional<Author> findByName(String name);

    Optional<Long> findIdByName(String name);

    List<Author> findAll();

    void delete(long id);
}
