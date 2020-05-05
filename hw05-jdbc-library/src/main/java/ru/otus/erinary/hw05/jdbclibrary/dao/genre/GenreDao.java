package ru.otus.erinary.hw05.jdbclibrary.dao.genre;

import ru.otus.erinary.hw05.jdbclibrary.model.Genre;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс репозитория для {@link Genre}
 */
public interface GenreDao {

    long save(Genre genre);

    Optional<Genre> findById(long id);

    List<Genre> findAll();

    void delete(long id);
}
