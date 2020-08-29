package ru.otus.erinary.hw11.library.dao.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.erinary.hw11.library.dao.model.Genre;

/**
 * Интерфейс репозитория для {@link Genre}
 */
public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {

    Mono<Genre> findByName(String name);

}
