package ru.otus.erinary.hw07.springdatalibrary.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.erinary.hw07.springdatalibrary.entity.Genre;

import java.util.Optional;

/**
 * Repository interface for {@link Genre}.
 */
public interface GenreRepository extends JpaRepository<Genre, Long> {

    /**
     * Searches the genre by given name.
     *
     * @param name genre's name
     * @return a genre
     */
    Optional<Genre> findByName(String name);

    /**
     * Searches the genre's id by given name.
     *
     * @param name genre's name
     * @return genre's id
     */
    @Query("select g.id from Genre g where g.name = :name")
    Optional<Long> findIdByName(@Param("name") String name);

}
