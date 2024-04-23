package ru.otus.erinary.hw06.hibernatelibrary.dao.genre;

import ru.otus.erinary.hw06.hibernatelibrary.model.Genre;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for {@link Genre}.
 */
public interface GenreRepository {

    /**
     * Insert a new genre's record into database.
     *
     * @param genre {@link Genre}
     * @return genre's id
     */
    Long insert(Genre genre);

    /**
     * Updates the existing genre's record.
     *
     * @param genre {@link Genre}
     */
    void update(Genre genre);

    /**
     * Searches the genre by given id.
     *
     * @param id genre's id
     * @return a genre
     */
    Optional<Genre> findById(Long id);

    /**
     * Searches the genre by given name.
     *
     * @param name genre's name
     * @return a genre
     */
    Optional<Genre> findByName(String name);

    /**
     * Searches the genre's id by given name.
     *
     * @param name genre's name
     * @return genre's id
     */
    Optional<Long> findIdByName(String name);

    /**
     * Returns a list of all genres.
     *
     * @return genres
     */
    List<Genre> findAll();

    /**
     * Deletes the genre.
     *
     * @param id genre's id
     */
    void delete(Long id);
}
