package ru.otus.erinary.hw11.library.dao.changelog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.erinary.hw11.library.dao.model.Book;

import java.util.Optional;

/**
 * Интерфейс репозитория для {@link Book}
 */
public interface BookSyncRepository extends MongoRepository<Book, String> {
    Optional<Book> findByTitle(String title);
}
