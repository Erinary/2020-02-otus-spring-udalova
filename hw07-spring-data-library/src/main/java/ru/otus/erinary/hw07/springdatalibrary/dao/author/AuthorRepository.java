package ru.otus.erinary.hw07.springdatalibrary.dao.author;

import ru.otus.erinary.hw07.springdatalibrary.model.Author;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс репозитория для {@link Author}
 */
public interface AuthorRepository {

    Long insert(Author author);

    void update(Author author);

    Optional<Author> findById(Long id);

    Optional<Author> findByName(String name);

    Optional<Long> findIdByName(String name);

    List<Author> findAll();

    void delete(Long id);
}
