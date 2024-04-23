package ru.otus.erinary.hw06.hibernatelibrary.dao.comment;

import org.springframework.stereotype.Repository;
import ru.otus.erinary.hw06.hibernatelibrary.model.Comment;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

/**
 * Realization of {@link CommentRepository}.
 */
@Repository
public class CommentRepositoryImpl implements CommentRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Long insert(final Comment comment) {
        manager.persist(comment);
        return comment.getId();
    }

    @Override
    public Optional<Comment> findById(final Long id) {
        return Optional.ofNullable(manager.find(Comment.class, id));
    }

    @Override
    public List<Comment> findAll() {
        return manager.createQuery("select c from Comment c", Comment.class).getResultList();
    }

    @Override
    public List<Comment> findAllByBookId(final Long bookId) {
        var query = manager.createQuery("select c from Comment c where c.book.id = :book_id", Comment.class);
        query.setParameter("book_id", bookId);
        return query.getResultList();
    }

    @Override
    public void delete(final Long id) {
        var query = manager.createQuery("delete from Comment c where c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
