package ru.otus.erinary.hw07.springdatalibrary.dao.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.erinary.hw07.springdatalibrary.model.Comment;

import java.util.List;

/**
 * Repository interface for {@link Comment}.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Searches all comments related to the given book.
     *
     * @param bookId book's id
     * @return list of related comments
     */
    List<Comment> findAllByBookId(Long bookId);

}
