package ru.otus.erinary.hw11.library.dao.changelog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.erinary.hw11.library.dao.model.Genre;

import java.util.Optional;

/**
 * Интерфейс репозитория для {@link Genre}
 */
public interface GenreSyncRepository extends MongoRepository<Genre, String> {
    Optional<Genre> findByName(String name);
}
