package ru.otus.erinary.hw13.library.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.erinary.hw13.library.dao.model.Comment;

import java.util.List;

/**
 * Интерфейс репозитория для {@link Comment}
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByBookId(Long bookId);

    @Query("select c.book.id from Comment c where c.id = :id")
    Long findBookIdById(@Param("id") Long id);
}
