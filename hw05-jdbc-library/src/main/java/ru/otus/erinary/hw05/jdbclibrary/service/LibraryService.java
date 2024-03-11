package ru.otus.erinary.hw05.jdbclibrary.service;

import ru.otus.erinary.hw05.jdbclibrary.model.Author;
import ru.otus.erinary.hw05.jdbclibrary.model.Book;
import ru.otus.erinary.hw05.jdbclibrary.model.Genre;

import java.util.List;

/**
 * Interface that represents a library storage and used for CRUD operations on main entities.
 */
public interface LibraryService {

    /**
     * Returns a list of all authors.
     *
     * @return all authors
     */
    List<Author> getAuthors();

    /**
     * Searches the author by given name.
     *
     * @param name author's name
     * @return an author
     */
    Author getAuthorByName(String name);

    /**
     * Deletes the author.
     *
     * @param id author's id
     */
    void deleteAuthor(Long id);

    /**
     * Returns a list of all genres.
     *
     * @return all genres
     */
    List<Genre> getGenres();

    /**
     * Searches the genre by given name.
     *
     * @param name genre's name
     * @return a genre
     */
    Genre getGenreByName(String name);

    /**
     * Deletes the genre.
     *
     * @param id genre's id
     */
    void deleteGenre(Long id);

    /**
     * Returns a list of all books.
     *
     * @return books
     */
    List<Book> getBooks();

    /**
     * Searches the book bi given id.
     *
     * @param id book's id
     * @return a book
     */
    Book getBookById(Long id);

    /**
     * Saves a new book.
     *
     * @param id         book's id
     * @param title      book's name
     * @param year       publishing year
     * @param authorName author's name
     * @param genreName  genre
     * @return saved book
     */
    Book saveBook(Long id, String title, int year, String authorName, String genreName);

    /**
     * Deletes the book.
     *
     * @param id book's id
     */
    void deleteBook(Long id);
}
