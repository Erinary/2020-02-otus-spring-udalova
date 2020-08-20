package ru.otus.erinary.hw08.library.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.erinary.hw08.library.dao.model.Genre;

import java.util.Optional;

/**
 * Интерфейс репозитория для {@link Genre}
 */
public interface GenreRepository extends MongoRepository<Genre, Long> {

    Optional<Genre> findById(Long id);

    Optional<Genre> findByName(String name);

//    @Query("select g.id from Genre g where g.name = :name")
    Optional<Long> findIdByName(@Param("name") String name);

}
