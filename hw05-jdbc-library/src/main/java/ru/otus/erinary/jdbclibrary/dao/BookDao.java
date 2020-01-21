package ru.otus.erinary.jdbclibrary.dao;

import ru.otus.erinary.jdbclibrary.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    long save(Book book);

    Optional<Book> findById(long id);

    List<Book> findAll();

    void delete(long id);
}
