package ru.otus.erinary.hw07.springdatalibrary.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.erinary.hw07.springdatalibrary.entity.Author;

import java.util.Optional;

/**
 * Repository interface for {@link Author}.
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {

    /**
     * Searches the author by given name.
     *
     * @param name author's name
     * @return an author
     */
    Optional<Author> findByName(String name);

    /**
     * Searches the author's id by given name.
     *
     * @param name author's name
     * @return author's id
     */
    @Query("select a.id from Author a where a.name = :name")
    Optional<Long> findIdByName(@Param("name") String name);

}
