package ru.otus.erinary.hw06.hibernatelibrary.dao.author;

import org.springframework.stereotype.Repository;
import ru.otus.erinary.hw06.hibernatelibrary.model.Author;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Имплементация {@link AuthorRepository}
 */
@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Long insert(final Author author) {
        manager.persist(author);
        return author.getId();
    }

    @Override
    public void update(final Author author) {
        manager.merge(author);
    }

    @Override
    public Optional<Author> findById(final Long id) {
        return Optional.ofNullable(manager.find(Author.class, id));
    }

    @Override
    public Optional<Author> findByName(final String name) {
        var query = manager.createQuery("select a from Author a where a.name = :name", Author.class);
        query.setParameter("name", name);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Long> findIdByName(final String name) {
        var query = manager.createQuery("select a.id from Author a where a.name = :name", Long.class);
        query.setParameter("name", name);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public List<Author> findAll() {
        return manager.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    public void delete(final Long id) {
        var query = manager.createQuery("delete from Author a where a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

}
