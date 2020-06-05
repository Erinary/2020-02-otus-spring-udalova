package ru.otus.erinary.hw05.jdbclibrary.dao.genre;

import ru.otus.erinary.hw05.jdbclibrary.model.Genre;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс репозитория для {@link Genre}
 */
public interface GenreDao {

    Long insert(Genre genre);

    void update(Genre genre);

    Optional<Genre> findById(Long id);

    Optional<Genre> findByName(String name);

    Optional<Long> findIdByName(String name);

    List<Genre> findAll();

    void delete(Long id);
}
