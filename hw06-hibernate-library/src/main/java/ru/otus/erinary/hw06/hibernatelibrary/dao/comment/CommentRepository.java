package ru.otus.erinary.hw06.hibernatelibrary.dao.comment;

import ru.otus.erinary.hw06.hibernatelibrary.model.Comment;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for {@link Comment}.
 */
public interface CommentRepository {

    /**
     * Insert a new comment's record into the database.
     *
     * @param comment {@link Comment}
     * @return comment's id
     */
    Long insert(Comment comment);

    /**
     * Searches the comment by given id.
     *
     * @param id comment's id
     * @return a comment
     */
    Optional<Comment> findById(Long id);

    /**
     * Returns a list of all comments.
     *
     * @return comments
     */
    List<Comment> findAll();

    /**
     * Searches all comments related to the given book.
     *
     * @param bookId book's id
     * @return list of related comments
     */
    List<Comment> findAllByBookId(Long bookId);

    /**
     * Deletes the comment.
     *
     * @param id comment's id
     */
    void delete(Long id);
}
