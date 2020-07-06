package ru.otus.erinary.hw06.hibernatelibrary.dao.genre;

import ru.otus.erinary.hw06.hibernatelibrary.model.Genre;

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
