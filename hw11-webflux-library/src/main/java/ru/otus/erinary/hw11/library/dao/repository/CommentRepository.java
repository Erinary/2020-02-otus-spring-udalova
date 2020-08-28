package ru.otus.erinary.hw11.library.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.erinary.hw11.library.dao.model.Comment;

import java.util.List;

/**
 * Интерфейс репозитория для {@link Comment}
 */
public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findAllByBookId(String bookId);

}
