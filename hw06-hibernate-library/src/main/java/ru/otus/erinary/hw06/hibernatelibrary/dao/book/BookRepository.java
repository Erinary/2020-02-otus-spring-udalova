package ru.otus.erinary.hw06.hibernatelibrary.dao.book;

import ru.otus.erinary.hw06.hibernatelibrary.model.Book;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for {@link Book}.
 */
public interface BookRepository {

    /**
     * Saves the book into the database.
     *
     * @param book {@link Book}
     * @return saved book
     */
    Book save(Book book);

    /**
     * Searches the book by given id.
     *
     * @param id book's id
     * @return a book
     */
    Optional<Book> findById(Long id);

    /**
     * Returns a list of all books.
     *
     * @return books
     */
    List<Book> findAll();

    /**
     * Searches all books, related to specified author.
     *
     * @param authorId author's id
     * @return list of the books
     */
    List<Book> findAllByAuthorId(Long authorId);

    /**
     * Searches all books, related to specified genre.
     *
     * @param genreId genre's id
     * @return list of the books
     */
    List<Book> findAllByGenreId(Long genreId);

    /**
     * Deletes the book.
     *
     * @param id book's id
     */
    void delete(Long id);
}
