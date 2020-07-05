package ru.otus.erinary.hw07.springdatalibrary.dao.genre;

import ru.otus.erinary.hw07.springdatalibrary.model.Genre;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс репозитория для {@link Genre}
 */
public interface GenreRepository {

    Long insert(Genre genre);

    void update(Genre genre);

    Optional<Genre> findById(Long id);

    Optional<Genre> findByName(String name);

    Optional<Long> findIdByName(String name);

    List<Genre> findAll();

    void delete(Long id);
}
