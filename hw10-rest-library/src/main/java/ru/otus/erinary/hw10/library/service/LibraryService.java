package ru.otus.erinary.hw10.library.service;

import ru.otus.erinary.hw10.library.model.Author;
import ru.otus.erinary.hw10.library.model.Book;
import ru.otus.erinary.hw10.library.model.Comment;
import ru.otus.erinary.hw10.library.model.Genre;

import java.util.List;

public interface LibraryService {

    List<Author> getAuthors();

    Author getAuthorById(Long id);

    Author getAuthorByName(String name);

    void deleteAuthor(Long id);

    List<Genre> getGenres();

    Genre getGenreById(Long id);

    Genre getGenreByName(String name);

    void deleteGenre(Long id);

    List<Book> getBooks();

    Book getBookById(Long id);

    Book saveBook(Book book);

    void deleteBook(Long id);

    List<Comment> getBookComments(Long bookId);

    Comment saveComment(String text, String user, Long bookId);

    void deleteComment(Long id);

    Long getBookIdByComment(Long commentId);
}
