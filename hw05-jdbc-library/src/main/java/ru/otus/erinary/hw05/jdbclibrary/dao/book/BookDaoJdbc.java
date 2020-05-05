package ru.otus.erinary.hw05.jdbclibrary.dao.book;

import ru.otus.erinary.hw05.jdbclibrary.model.Book;

import java.util.List;
import java.util.Optional;

/**
 * Имплементация {@link BookDao}
 */
public class BookDaoJdbc implements BookDao {
    @Override
    public long save(Book book) {
        return 0;
    }

    @Override
    public Optional<Book> findById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
