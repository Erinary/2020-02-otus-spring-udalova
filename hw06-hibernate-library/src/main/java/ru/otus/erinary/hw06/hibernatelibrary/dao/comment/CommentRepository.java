package ru.otus.erinary.hw06.hibernatelibrary.dao.comment;

import ru.otus.erinary.hw06.hibernatelibrary.model.Comment;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс репозитория для {@link Comment}
 */
public interface CommentRepository {

    Long insert(Comment comment);

    Optional<Comment> findById(Long id);

    List<Comment> findAll();

    List<Comment> findAllByBookId(Long bookId);

    void delete(Long id);
}
