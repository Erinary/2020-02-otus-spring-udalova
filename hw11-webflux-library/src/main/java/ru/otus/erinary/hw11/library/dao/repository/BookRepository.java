package ru.otus.erinary.hw11.library.dao.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.erinary.hw11.library.dao.model.Book;

/**
 * Интерфейс репозитория для {@link Book}
 */
public interface BookRepository extends ReactiveMongoRepository<Book, String> {

    Flux<Book> findAllByAuthorId(String authorId);

    Flux<Book> findAllByGenreId(String genreId);

}
