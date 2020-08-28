package ru.otus.erinary.hw11.library.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.erinary.hw11.library.dao.model.Author;

import java.util.Optional;

/**
 * Интерфейс репозитория для {@link Author}
 */
public interface AuthorRepository extends MongoRepository<Author, String> {

    Optional<Author> findByName(String name);

}
