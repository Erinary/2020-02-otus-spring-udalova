package ru.otus.erinary.hw09.library.dao.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.erinary.hw09.library.model.Comment;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс репозитория для {@link Comment}
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findById(Long id);

    List<Comment> findAllByBookId(Long bookId);

}