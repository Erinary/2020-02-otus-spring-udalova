package ru.otus.erinary.hw12.library.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.erinary.hw12.library.dao.model.Author;

import java.util.Optional;

/**
 * Интерфейс репозитория для {@link Author}
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findById(Long id);

    Optional<Author> findByName(String name);

    @Query("select a.id from Author a where a.name = :name")
    Optional<Long> findIdByName(@Param("name") String name);

}
