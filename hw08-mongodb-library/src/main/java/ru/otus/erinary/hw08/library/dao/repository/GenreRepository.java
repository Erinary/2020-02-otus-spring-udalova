package ru.otus.erinary.hw08.library.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.erinary.hw08.library.entity.Genre;

import java.util.Optional;

/**
 * Repository interface for {@link Genre}.
 */
public interface GenreRepository extends MongoRepository<Genre, String> {

    /**
     * Searches the genre by given name.
     *
     * @param name genre's name
     * @return a genre
     */
    Optional<Genre> findByName(String name);

}
