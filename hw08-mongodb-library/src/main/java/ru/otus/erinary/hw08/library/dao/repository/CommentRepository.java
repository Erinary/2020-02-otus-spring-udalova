package ru.otus.erinary.hw08.library.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.erinary.hw08.library.dao.model.Comment;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс репозитория для {@link Comment}
 */
@SuppressWarnings("NullableProblems")
public interface CommentRepository extends MongoRepository<Comment, String> {

    Optional<Comment> findById(String id);

    List<Comment> findAllByBookId(String bookId);

    void deleteById(String id);

}
