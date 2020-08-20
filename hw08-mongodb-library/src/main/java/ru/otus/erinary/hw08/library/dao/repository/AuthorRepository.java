package ru.otus.erinary.hw08.library.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.erinary.hw08.library.dao.model.Author;

import java.util.Optional;

/**
 * Интерфейс репозитория для {@link Author}
 */
public interface AuthorRepository extends MongoRepository<Author, String> {

    Optional<Author> findById(String id);

    Optional<Author> findByName(String name);

    @Query("{'name' : :#{#name}}")
    Optional<String> findIdByName(@Param("name") String name);

    void deleteById(String id);

}
