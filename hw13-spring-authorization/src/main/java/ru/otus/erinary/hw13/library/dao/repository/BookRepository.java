package ru.otus.erinary.hw13.library.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.erinary.hw13.library.dao.model.Book;

import java.util.List;

/**
 * Интерфейс репозитория для {@link Book}
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByAuthorId(Long authorId);

    List<Book> findAllByGenreId(Long genreId);

}
