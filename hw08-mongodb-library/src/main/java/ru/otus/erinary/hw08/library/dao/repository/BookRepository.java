package ru.otus.erinary.hw08.library.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.erinary.hw08.library.entity.Book;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for {@link Book}.
 */
public interface BookRepository extends MongoRepository<Book, String> {

    /**
     * Searches a book by the given title.
     *
     * @param title the title of the book
     * @return an Optional containing the matching book
     */
    Optional<Book> findByTitle(String title);

    /**
     * Searches all books, related to specified author.
     *
     * @param authorId author's id
     * @return list of the books
     */
    List<Book> findAllByAuthorId(String authorId);

    /**
     * Searches all books, related to specified genre.
     *
     * @param genreId genre's id
     * @return list of the books
     */
    List<Book> findAllByGenreId(String genreId);

}
