package ru.otus.erinary.hw11.library.dao.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.erinary.hw11.library.dao.model.Author;

/**
 * Интерфейс репозитория для {@link Author}
 */
public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {

    Mono<Author> findByName(String name);

}
