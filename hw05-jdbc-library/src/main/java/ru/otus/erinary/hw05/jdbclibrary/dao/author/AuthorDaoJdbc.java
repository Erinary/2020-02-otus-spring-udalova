package ru.otus.erinary.hw05.jdbclibrary.dao.author;

import ru.otus.erinary.hw05.jdbclibrary.model.Author;

import java.util.List;
import java.util.Optional;

/**
 * Имплементация {@link AuthorDao}
 */
public class AuthorDaoJdbc implements AuthorDao {
    @Override
    public long save(Author author) {
        return 0;
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Author> findAll() {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
