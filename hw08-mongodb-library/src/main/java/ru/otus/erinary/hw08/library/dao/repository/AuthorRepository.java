package ru.otus.erinary.hw08.library.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.erinary.hw08.library.entity.Author;

import java.util.Optional;

/**
 * Repository interface for {@link Author}.
 */
public interface AuthorRepository extends MongoRepository<Author, String> {

    /**
     * Searches an author by the given name.
     *
     * @param name author's name
     * @return an author
     */
    Optional<Author> findByName(String name);

}
