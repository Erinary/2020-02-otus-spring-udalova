package ru.otus.erinary.hw08.library.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.erinary.hw08.library.dao.model.Book;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс репозитория для {@link Book}
 */
@SuppressWarnings("NullableProblems")
public interface BookRepository extends MongoRepository<Book, String> {

    Optional<Book> findById(String id);

    List<Book> findAllByAuthorId(String authorId);

    List<Book> findAllByGenreId(String genreId);

    void deleteById(String id);

}
