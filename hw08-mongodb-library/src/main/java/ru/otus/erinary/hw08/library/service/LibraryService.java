package ru.otus.erinary.hw08.library.service;

import ru.otus.erinary.hw08.library.dao.model.Author;
import ru.otus.erinary.hw08.library.dao.model.Book;
import ru.otus.erinary.hw08.library.dao.model.Comment;
import ru.otus.erinary.hw08.library.dao.model.Genre;

import java.util.List;

public interface LibraryService {

    List<Author> getAuthors();

    Author getAuthorByName(String name);

    void deleteAuthor(String id);

    List<Genre> getGenres();

    Genre getGenreByName(String name);

    void deleteGenre(String id);

    List<Book> getBooks();

    Book getBookById(String id);

    Book saveBook(String id, String title, int year, String authorName, String genreName);

    void deleteBook(String id);

    List<Comment> getBookComments(String bookId);

    Long saveComment(String text, String user, String bookId);

    void deleteComment(Long id);
}
