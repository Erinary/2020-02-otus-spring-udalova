package ru.otus.erinary.hw10.library.dao.genre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.erinary.hw10.library.model.Genre;

import java.util.Optional;

/**
 * Интерфейс репозитория для {@link Genre}
 */
public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> findById(Long id);

    Optional<Genre> findByName(String name);

    @Query("select g.id from Genre g where g.name = :name")
    Optional<Long> findIdByName(@Param("name") String name);

}
