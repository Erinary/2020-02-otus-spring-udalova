package ru.otus.erinary.hw08.library.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.erinary.hw08.library.dao.model.Genre;

import java.util.Optional;

/**
 * Интерфейс репозитория для {@link Genre}
 */
@SuppressWarnings("NullableProblems")
public interface GenreRepository extends MongoRepository<Genre, String> {

    Optional<Genre> findById(String id);

    Optional<Genre> findByName(String name);

    void deleteById(String id);

}
