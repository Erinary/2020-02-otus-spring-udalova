package ru.otus.erinary.hw05.jdbclibrary.dao.genre;

import ru.otus.erinary.hw05.jdbclibrary.model.Genre;

import java.util.List;
import java.util.Optional;

/**
 * Имплементация {@link GenreDao}
 */
public class GenreDaoJdbc implements GenreDao {

    @Override
    public long save(Genre genre) {
        return 0;
    }

    @Override
    public Optional<Genre> findById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Genre> findAll() {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
