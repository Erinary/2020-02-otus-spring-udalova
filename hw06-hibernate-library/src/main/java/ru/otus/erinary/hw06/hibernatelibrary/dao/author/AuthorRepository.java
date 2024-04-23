package ru.otus.erinary.hw06.hibernatelibrary.dao.author;

import ru.otus.erinary.hw06.hibernatelibrary.model.Author;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for {@link Author}.
 */
public interface AuthorRepository {

    /**
     * Inserts a new author's record into the database.
     *
     * @param author {@link Author}
     * @return author's id
     */
    Long insert(Author author);

    /**
     * Updates the existing author's record.
     *
     * @param author {@link Author}
     */
    void update(Author author);

    /**
     * Searches the author by given id.
     *
     * @param id author's id
     * @return an author
     */
    Optional<Author> findById(Long id);

    /**
     * Searches the author by given name.
     *
     * @param name author's name
     * @return an author
     */
    Optional<Author> findByName(String name);

    /**
     * Searches the author's id by given name.
     *
     * @param name author's name
     * @return author's id
     */
    Optional<Long> findIdByName(String name);

    /**
     * Returns a list of all authors.
     *
     * @return authors
     */
    List<Author> findAll();

    /**
     * Deletes the author.
     *
     * @param id author's id
     */
    void delete(Long id);
}
