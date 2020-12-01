package ru.otus.erinary.hw11.library.dao.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.erinary.hw11.library.dao.model.Comment;

/**
 * Интерфейс репозитория для {@link Comment}
 */
public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {

    Flux<Comment> findAllByBookId(String bookId);

}
