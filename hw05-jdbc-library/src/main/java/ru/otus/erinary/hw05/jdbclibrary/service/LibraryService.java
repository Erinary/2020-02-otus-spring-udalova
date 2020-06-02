package ru.otus.erinary.hw05.jdbclibrary.service;

import ru.otus.erinary.hw05.jdbclibrary.model.Author;
import ru.otus.erinary.hw05.jdbclibrary.model.Book;
import ru.otus.erinary.hw05.jdbclibrary.model.Genre;

import java.util.List;

public interface LibraryService {

    List<Author> getAuthors();

    Author getAuthorByName(String name);

    void deleteAuthor(long id);

    List<Genre> getGenres();

    Genre getGenreByName(String name);

    void deleteGenre(long id);

    List<Book> getBooks();

    Book getBookById(long id);

    Book saveBook(long id, String title, int year, String authorName, String genreName);

    void deleteBook(long id);
}
