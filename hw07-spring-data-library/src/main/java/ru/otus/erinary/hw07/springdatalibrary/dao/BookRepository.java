package ru.otus.erinary.hw07.springdatalibrary.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.erinary.hw07.springdatalibrary.entity.Book;

import java.util.List;

/**
 * Repository interface for {@link Book}.
 */
public interface BookRepository extends JpaRepository<Book, Long> {

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

}
