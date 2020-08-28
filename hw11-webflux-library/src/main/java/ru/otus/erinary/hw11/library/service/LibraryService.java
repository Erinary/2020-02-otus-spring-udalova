package ru.otus.erinary.hw11.library.service;

import ru.otus.erinary.hw11.library.dao.model.Author;
import ru.otus.erinary.hw11.library.dao.model.Book;
import ru.otus.erinary.hw11.library.dao.model.Comment;
import ru.otus.erinary.hw11.library.dao.model.Genre;

import java.util.List;

public interface LibraryService {

    List<Author> getAuthors();

    Author getAuthorById(String id);

    Author getAuthorByName(String name);

    void deleteAuthor(String id);

    List<Genre> getGenres();

    Genre getGenreById(String id);

    Genre getGenreByName(String name);

    void deleteGenre(String id);

    List<Book> getBooks();

    Book getBookById(String id);

    Book saveBook(Book book);

    void deleteBook(String id);

    List<Comment> getBookComments(String bookId);

    Comment saveComment(String text, String user, String bookId);

    void deleteComment(String id);

    String getBookIdByComment(String commentId);
}
