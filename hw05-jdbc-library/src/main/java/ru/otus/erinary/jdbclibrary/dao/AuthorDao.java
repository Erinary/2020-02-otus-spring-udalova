package ru.otus.erinary.jdbclibrary.dao;

import ru.otus.erinary.jdbclibrary.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    long save(Author author);

    Optional<Author> findById(long id);

    List<Author> findAll();

    void delete(long id);
}
