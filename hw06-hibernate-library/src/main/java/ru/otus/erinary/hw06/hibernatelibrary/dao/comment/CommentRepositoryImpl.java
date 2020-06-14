package ru.otus.erinary.hw06.hibernatelibrary.dao.comment;

import org.springframework.stereotype.Repository;
import ru.otus.erinary.hw06.hibernatelibrary.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Имплементация {@link CommentRepository}
 */
@Repository
@Transactional
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
        var query = manager.createQuery("select c from Comment c where c.id = :id", Comment.class);
        query.setParameter("id", id);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Comment> findAll() {
        return manager.createQuery("select c from Comment c", Comment.class).getResultList();
    }

    @Override
    public List<Comment> findAllByBookId(Long bookId) {
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
