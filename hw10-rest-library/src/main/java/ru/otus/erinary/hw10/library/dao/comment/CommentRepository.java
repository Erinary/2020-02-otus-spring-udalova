package ru.otus.erinary.hw10.library.dao.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.erinary.hw10.library.model.Comment;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс репозитория для {@link Comment}
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findById(Long id);

    List<Comment> findAllByBookId(Long bookId);

    @Query("select c.book.id from Comment c where c.id = :id")
    Long findBookIdById(@Param("id") Long id);
}
