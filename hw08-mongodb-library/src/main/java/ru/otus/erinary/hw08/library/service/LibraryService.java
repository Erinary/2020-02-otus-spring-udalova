package ru.otus.erinary.hw08.library.service;

import ru.otus.erinary.hw08.library.api.model.AuthorModel;
import ru.otus.erinary.hw08.library.api.model.BookModel;
import ru.otus.erinary.hw08.library.api.model.CommentModel;
import ru.otus.erinary.hw08.library.api.model.GenreModel;

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
    List<AuthorModel> getAuthors();

    /**
     * Searches the author by given name.
     *
     * @param name author's name
     * @return an author
     */
    AuthorModel getAuthorByName(String name);

    /**
     * Deletes the author.
     *
     * @param id author's id
     */
    void deleteAuthor(String id);

    /**
     * Returns a list of all genres.
     *
     * @return all genres
     */
    List<GenreModel> getGenres();

    /**
     * Searches the genre by given name.
     *
     * @param name genre's name
     * @return a genre
     */
    GenreModel getGenreByName(String name);

    /**
     * Deletes the genre.
     *
     * @param id genre's id
     */
    void deleteGenre(String id);

    /**
     * Returns a list of all books.
     *
     * @return books
     */
    List<BookModel> getBooks();

    /**
     * Searches the book by given id.
     *
     * @param id book's id
     * @return a book
     */
    BookModel getBookById(String id);

    /**
     * Saves a new book or updates an existing.
     *
     * @param model book's model for saving or updating
     * @return saved book
     */
    BookModel saveBook(BookModel model);

    /**
     * Deletes the book.
     *
     * @param id book's id
     */
    void deleteBook(String id);

    /**
     * Returns a list of book's comments.
     *
     * @param bookId book's id
     * @return list of comments
     */
    List<CommentModel> getBookComments(String bookId);

    /**
     * Saves a new comment.
     *
     * @param model comment's model for saving or updating
     * @return id of the saved comment
     */
    String saveComment(CommentModel model);

    /**
     * Deletes the comment.
     *
     * @param id comment's id
     */
    void deleteComment(String id);
}
