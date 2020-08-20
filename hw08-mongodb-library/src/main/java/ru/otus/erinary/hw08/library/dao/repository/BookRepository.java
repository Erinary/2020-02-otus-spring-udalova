package ru.otus.erinary.hw08.library.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.erinary.hw08.library.dao.model.Book;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс репозитория для {@link Book}
 */
public interface BookRepository extends MongoRepository<Book, Long> {

    Optional<Book> findById(Long id);

    List<Book> findAllByAuthorId(String authorId);

    List<Book> findAllByGenreId(Long genreId);

}
