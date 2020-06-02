package ru.otus.erinary.hw05.jdbclibrary.service;

import ru.otus.erinary.hw05.jdbclibrary.model.Author;
import ru.otus.erinary.hw05.jdbclibrary.model.Book;
import ru.otus.erinary.hw05.jdbclibrary.model.Genre;

import java.util.List;

public interface LibraryService {

    List<Author> getAuthors();

    Author getAuthorById(long id);

    Author getAuthorByName(String name);

    List<Genre> getGenres();

    Genre getGenreById(long id);

    Genre getGenreByName(String name);

    List<Book> getBooks();

    Book getBookById(long id);

    Book saveBook(Book book);

    void deleteBook(long id);
}
