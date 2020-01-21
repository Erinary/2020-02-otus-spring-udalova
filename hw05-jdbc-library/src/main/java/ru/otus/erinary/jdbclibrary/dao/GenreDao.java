package ru.otus.erinary.jdbclibrary.dao;

import ru.otus.erinary.jdbclibrary.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {

    long save(Genre genre);

    Optional<Genre> findById(long id);

    List<Genre> findAll();

    void delete(long id);
}
